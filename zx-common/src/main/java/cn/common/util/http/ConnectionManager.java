package cn.common.util.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * http客户端
 */
public class ConnectionManager {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	/**
	 * 连接池
	 */
    private static PoolingHttpClientConnectionManager cm = null;
    /**
     * 存活策略
     */
    private static ConnectionKeepAliveStrategy strategy = null;
    /**
     * 重试策略
     */
    private static HttpRequestRetryHandler retryHandler = null;
    /**
     * 代理路径
     */
    private static String proxyUri;
    /**
     * 代理端口
     */
    private static int proxyPort;
    /**
     * 代理协议
     */
    private static String proxyScheme;
    /**
     * 代理名称
     */
    private static String proxyUserName;
    /**
     * 代理密码
     */
    private static String proxyPassword;
    /**
     * 代理密码
     */
    private static HttpClientBuilder httpClientBuilder;

    public CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = httpClientBuilder.build();
                
        /*CloseableHttpClient httpClient = HttpClients.createDefault();//如果不采用连接池就是这种方式获取连接*/
        return httpClient;
    }
    
    static {
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm =new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(1024);
        
        new Thread() {
        	public void run() {
	        	 boolean shutdown = false;
	        	 int connectRecyclingTime = 10000;
	        	 int connectFreeTimeout = 30;
	        	 try {
	                 while (!shutdown) {
	                     synchronized (this) {
	                         wait(connectRecyclingTime);
	                         // Close expired connections
	                         cm.closeExpiredConnections();
	                         // Optionally, close connections
	                         // that have been idle longer than 30 sec
	                         cm.closeIdleConnections(connectFreeTimeout, TimeUnit.SECONDS);
	                     }
	                 }
	             } catch (InterruptedException ex) {
	                 // terminate
	             	logger.error("idle connections monitor thread happened a exception.{}",ex);
	             }
        	}
        }.start();
        
        strategy = (HttpResponse response, HttpContext context) -> {
            // Honor 'keep-alive' header
            HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    return Long.parseLong(value) * 1000;
                }
            }
            return 30000;
        };
        
    	retryHandler =  (IOException exception, int executionCount, HttpContext context) -> {
            if (executionCount >= 2 /*** 超过重试次数 ***/) {
                // Do not retry if over max retry count
                return false;
            }
            
            if(exception instanceof SocketTimeoutException /*** socket传输超时时出现的错误 ***/){
            	//Socket Timeout often already processed
            	return false;
            }
            
            if (exception instanceof InterruptedIOException /*** IO中断时抛出的异常 ***/) {
                // Timeout
                return false;
            }
            if (exception instanceof UnknownHostException /*** 域名解析失败 ***/) {
                // Unknown host
                return false;
            }
            if (exception instanceof SSLException /*** SSL处理失败 ***/) {
                // SSL handshake exception
                return false;
            }
            
//                HttpClientContext clientContext = HttpClientContext.adapt(context);
//                org.apache.http.HttpRequest request = clientContext.getRequest();
//                return !(request instanceof HttpEntityEnclosingRequest);
            return false;
        };
            
        httpClientBuilder = HttpClients.custom()
        .setConnectionManager(cm)
        .setKeepAliveStrategy(strategy)// 设置连接存活策略
        .setRetryHandler(retryHandler);
    	if (StringUtils.isNotEmpty(proxyUri) && proxyPort != 0) {
    		HttpHost httpHost = new HttpHost(proxyUri, proxyPort, proxyScheme);
    		httpClientBuilder.setProxy(httpHost);
    		if (!StringUtils.isNoneBlank(proxyUserName, proxyPassword)) {
    			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    			credentialsProvider.setCredentials(new AuthScope(httpHost),
    					new UsernamePasswordCredentials(proxyUserName, proxyPassword));
    			httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    		}
    	}
    }
    
    @PreDestroy
    public void destory() {
    	if(null != cm) {
    		cm.shutdown();
    	}
    }
}
