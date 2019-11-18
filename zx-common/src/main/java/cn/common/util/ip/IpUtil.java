package cn.common.util.ip;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * IP工具类
 * huangy 2018-08-18
 */
public class IpUtil {
    private static Logger logger= LoggerFactory.getLogger(IpUtil.class);
    /**
     * 描述：获取IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        if(ip!=null && ip.length()>15){ //"***.***.***.***".length() = 15
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * 描述：获取IP+[IP所属地址]
     */
    public static String getIpBelongAddress(HttpServletRequest request) {

        String ip = getIpAddress(request);
        String belongIp = getIPbelongAddress(ip);

        return ip + belongIp;
    }

    /**
     * 描述：获取IP所属地址
     */
    public static String getIPbelongAddress(String ip) {

        String ipAddress = "[]";
        try{
            //淘宝提供的服务地址
            String context = call("http://ip.taobao.com/service/getIpInfo.php?ip="+ip);
            JSONObject fromObject = JSONObject.parseObject(context);
            String code = fromObject.getString("code");
            if(code.equals("0")){
                JSONObject jsonObject = fromObject.getJSONObject("data");
                ipAddress =  "["+jsonObject.get("country")+"/" +jsonObject.get("city")+"]";
            }
        }catch(Exception e){
            logger.error("获取IP所属地址出错",e);
        }
        return ipAddress;
    }

    /**
     * 描述：获取Ip所属地址
     */
    public static String call(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setConnectTimeout(3000);
            httpCon.setDoInput(true);
            httpCon.setRequestMethod("GET");
            int code = httpCon.getResponseCode();
            if (code == 200) {
                return streamConvertToSting(httpCon.getInputStream());
            }
        } catch (Exception e) {
            logger.error("获取IP所属地址出错",e);
        }
        return null;
    }

    /**
     * 描述：将InputStream转换成String
     */
    public static String streamConvertToSting(InputStream is) {
        String tempStr = "";
        try {
            if (is == null) return null;
            ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
            byte[] by = new byte[1024];
            int len = 0;
            while ((len = is.read(by)) != -1) {
                arrayOut.write(by, 0, len);
            }
            tempStr = new String(arrayOut.toByteArray());
        } catch (IOException e) {
            logger.error("获取IP所属地址出错",e);
        }
        return tempStr;
    }
}
