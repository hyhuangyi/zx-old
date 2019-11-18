package cn.common.servlet;


import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IpConvert extends ClassicConverter {
    private static String localip;


    public IpConvert() {
    }

    public String convert(ILoggingEvent event) {
        return localip;
    }

    public static String getIp() {
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;

            while(netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = (NetworkInterface)netInterfaces.nextElement();
                Enumeration address = ni.getInetAddresses();

                while(address.hasMoreElements()) {
                    ip = (InetAddress)address.nextElement();
                    if ((ip.isSiteLocalAddress() || ip.isLoopbackAddress() || ip.getHostAddress().indexOf(":") != -1) && ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        localip = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException var5) {
            var5.printStackTrace();
        }

        return localip;
    }

    static {
        getIp();
    }
}
