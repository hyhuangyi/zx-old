package cn.common.util.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * http请求客户端
 */
public class HttpClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private static final Charset charset = Consts.UTF_8;

    private static ConnectionManager cm = new ConnectionManager();

    private static PoolingHttpClientConnectionManager connManager;

    static {
        //采用绕过验证的方式处理https请求
        SSLContext sslcontext = null;
        try {
            sslcontext = createIgnoreVerifySSL();
        } catch (Exception e) {
            logger.error("createIgnoreVerifySSL error : {}", e.getMessage(), e);
        }

        //设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();
        connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);

    }

    /**
     * 获取请求参数
     *
     * @param paramMap
     * @return
     * @author xiongxiaotun <br/>
     * @Title: getQueryString  <br/>
     * @version V1.0  <br/>
     */
    public static String getQueryString(Map<String, String> paramMap) {
        String queryString = "";
        if (MapUtils.isNotEmpty(paramMap)) {
            boolean first = true;
            for (String key : paramMap.keySet()) {
                String value = paramMap.get(key);
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                if (!first) {
                    queryString += "&";
                } else {
                    queryString += "?";
                    first = false;
                }
                queryString += key + "=";
                try {
                    queryString += URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return queryString;
    }

    /**
     * httpGet请求
     *
     * @param uri      uri地址
     * @param paramMap 请求参数
     * @return http返回值
     */
    public static String get(String uri, Map<String, String> paramMap) {
        return get(uri, paramMap, null);
    }

    /**
     * httpGet请求
     *
     * @param uri       uri地址
     * @param paramMap  请求参数
     * @param headerMap http请求头
     * @return http返回值
     */
    public static String get(String uri, Map<String, String> paramMap, Map<String, String> headerMap) {
        return getCall(uri + getQueryString(paramMap), headerMap);
    }

    /**
     * 发送get请求
     *
     * @param uri       uri地址
     * @param headerMap http请求头
     * @return http返回值
     */
    private static String getCall(String uri, Map<String, String> headerMap) {
        HttpGet httpGet = new HttpGet(uri);
        return httpCall(httpGet, headerMap);
    }

    /**
     * postFrom请求
     *
     * @param uri      uri地址
     * @param paramMap 请求参数
     * @return http返回值
     */
    public static String postForm(String uri, Map<String, String> paramMap) {
        return postForm(uri, paramMap, null);
    }

    /**
     * postFrom请求
     *
     * @param uri       uri地址
     * @param paramMap  请求参数
     * @param headerMap http请求头
     * @return http返回值
     */
    public static String postForm(String uri, Map<String, String> paramMap, Map<String, String> headerMap) {
        return postFormCall(uri, paramMap, headerMap);
    }

    /**
     * 发送post表单请求
     *
     * @param uri       uri地址
     * @param map       请求参数
     * @param headerMap http请求头
     * @return http返回值
     */
    private static String postFormCall(String uri, Map<String, String> map, Map<String, String> headerMap) {
        if (StringUtils.isBlank(uri)) {
            return null;
        }
        HttpPost httpPost = new HttpPost(uri);
        List<NameValuePair> formParams = map.entrySet().stream().
                map(p -> new BasicNameValuePair(p.getKey(), p.getValue())).collect(Collectors.toList());
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, charset);
        httpPost.setEntity(entity);
        return httpCall(httpPost, headerMap);
    }

    /**
     * postJson请求
     *
     * @param uri         uri地址
     * @param requestJson 请求参数
     * @return http返回值
     * @throws SocketTimeoutException
     */
    public static String postJson(String uri, String requestJson) {
        return postJson(uri, requestJson, null);
    }

    /**
     * postJson请求
     *
     * @param uri         uri地址
     * @param requestJson 请求参数
     * @param headerMap   http请求头
     * @return http返回值
     * @throws SocketTimeoutException
     */
    public static String postJson(String uri, String requestJson, Map<String, String> headerMap) {
        return postJsonCall(uri, requestJson, headerMap);
    }


    /**
     * @param uri         http请求地址
     * @param requestJson 请求内容
     * @return 返回值
     * @throws SocketTimeoutException
     */
    private static String postJsonCall(String uri, String requestJson, Map<String, String> headerMap) {
        return postCall(uri, requestJson, "application/json", headerMap);
    }

    /**
     * 以json格式发送post请求
     *
     * @param uri            uri地址
     * @param requestContent 请求参数
     * @param ctString       Content-Type
     * @param headerMap      http请求头
     * @return http返回值
     */
    private static String postCall(String uri, String requestContent, String ctString, Map<String, String> headerMap) {
        HttpPost httpPost = new HttpPost(uri);
        StringEntity stringEntity = new StringEntity(requestContent, charset);
        stringEntity.setContentType(ctString);
        httpPost.setEntity(stringEntity);
        return httpCall(httpPost, headerMap);
    }


    public static String postJsonWithoutSSL(String uri, String ctString) {
        HttpPost httpPost = new HttpPost(uri);
        StringEntity stringEntity = new StringEntity(ctString, charset);
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);

        CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(15000)
                .setConnectionRequestTimeout(15000)
                .setSocketTimeout(30000).build();
        httpPost.setConfig(requestConfig);

        try (CloseableHttpResponse response = client.execute(httpPost)) {
            Header[] responseHeaders = response.getHeaders("X-Ca-Error-Message");
            if (null != responseHeaders && responseHeaders.length > 0) {
                String errorMsg = responseHeaders[0].getValue();
                if (StringUtils.isNotBlank(errorMsg) && !"OK".equals(errorMsg)) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    if (responseBody.length() > 300) {
                        responseBody = responseBody.substring(0, 300);
                    }
                    logger.debug("请求响应:业务处理失败.状态码:{},响应结果:{}", response.getStatusLine().getStatusCode(), responseBody);
                    return null;
                }
            }
            if (response.getStatusLine().getStatusCode() != 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                if (responseBody.length() > 300) {
                    responseBody = responseBody.substring(0, 300);
                }
                logger.debug("请求响应:业务处理失败.状态码:{},响应结果:{}", response.getStatusLine().getStatusCode(), responseBody);
                return null;
            }
            return EntityUtils.toString(response.getEntity(), charset);
        } catch (ParseException | IOException e) {
            logger.error("请求响应:业务处理成功:结果解析失败.异常栈:{}", e);
        }
        return null;
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    /**
     * @param request   httpRequest
     * @param headerMap http请求头
     * @return http返回值
     * @throws IOException
     */
    private static String httpCall(HttpRequestBase request, Map<String, String> headerMap) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(15000)
                .setConnectionRequestTimeout(15000)
                .setSocketTimeout(30000).build();
        request.setConfig(requestConfig);

        Header[] headers = getHeaders(headerMap);
        if (headers.length != 0)
            request.setHeaders(headers);
        try (CloseableHttpResponse response = cm.getHttpClient().execute(request)) {
            Header[] responseHeaders = response.getHeaders("X-Ca-Error-Message");
            if (null != responseHeaders && responseHeaders.length > 0) {
                String errorMsg = responseHeaders[0].getValue();
                if (StringUtils.isNotBlank(errorMsg) && !"OK".equals(errorMsg)) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    if (responseBody.length() > 300) {
                        responseBody = responseBody.substring(0, 300);
                    }
                    logger.debug("请求响应:业务处理失败.状态码:{},响应结果:{}", response.getStatusLine().getStatusCode(), responseBody);
                    return null;
                }
            }
            if (response.getStatusLine().getStatusCode() != 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                if (responseBody.length() > 300) {
                    responseBody = responseBody.substring(0, 300);
                }
                logger.debug("请求响应:业务处理失败.状态码:{},响应结果:{}", response.getStatusLine().getStatusCode(), responseBody);
                return null;
            }
            return EntityUtils.toString(response.getEntity(), charset);
        } catch (ParseException | IOException e) {
            logger.error("请求响应:业务处理成功:结果解析失败.异常栈:{}", e);
        }
        return null;
    }


    /**
     * 返回标准的uri
     *
     * @param strUrl
     * @return
     */
    public static String getStandardUri(String strUrl) {
        try {
            URL url = new URL(strUrl);
            return new URI(url.getProtocol(), null, url.getHost(), url.getPort(), url.getPath(), url.getQuery(), null).toString();
        } catch (URISyntaxException | MalformedURLException e) {
            logger.error("创建标准Uri失败,strUrl:{}.异常栈:{}", strUrl, e);
        }
        return null;
    }

    /**
     * 返回http请求头列表
     *
     * @param headerMap
     * @return
     */
    private static Header[] getHeaders(Map<String, String> headerMap) {
        if (MapUtils.isEmpty(headerMap))
            return new Header[0];
        Header[] headers = new Header[headerMap.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            headers[i++] = new BasicHeader(entry.getKey(), entry.getValue());
        }
        return headers;
    }

    /**
     * 发送POST请求
     *
     * @param url   目标URL
     * @param param 参数，格式：name1=value1&name2=value2
     * @return 响应结果
     */
    public static String sendPost(String url, String param) throws IOException {
        StringBuilder result = new StringBuilder();
        URL realUrl = new URL(url);
        // 打开URL连接
        URLConnection conn = realUrl.openConnection();
        // 设置请求头
        conn = setDefaultHeaders(conn);
        conn.setConnectTimeout(30 * 1000);
        conn.setReadTimeout(30 * 1000);
        // 判断是否为JSON数据格式
        if (JSONObject.parse(param) != null) {
            conn.setRequestProperty("Content-Type", "application/json");
        }

        // POST请求必须设置
        conn.setDoOutput(true);
        conn.setDoInput(true);
        try (PrintWriter out = new PrintWriter(conn.getOutputStream())) {
            // 设置请求体
            out.print(param);
            // 发送请求体
            out.flush();
            // 设置输入流读取响应
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            }
        }
        return result.toString();
    }

    /**
     * 设置常用请求头
     *
     * @param connection 连接实体
     * @return 连接实体
     */
    private static URLConnection setDefaultHeaders(URLConnection connection) {
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        return connection;
    }

}
