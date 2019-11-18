package cn.common.util.sys;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class OpenUrl {
    public static String str="taskkill /F /IM chrome.exe";
    //指定打开网页的浏览器，后面这些参数就是网址，
    //实际上有文件来代替更好
    public static String str1="cmd /c start chrome "
            + "https://blog.csdn.net/qq_37209293/article/details/86481885 "
            + "https://blog.csdn.net/qq_37209293/article/details/84571027 "
            + "https://blog.csdn.net/qq_37209293/article/details/86014295 "
            + "https://blog.csdn.net/qq_37209293/article/details/82590855 "
            + "https://blog.csdn.net/qq_37209293/article/details/85613319 ";

    //我这里把要访问的网址分成了两部分，一次性访问大概二十个左右，浏览器不敢一次打开得太多，怕爆炸
    public static String str2="cmd /c start chrome "
            + "https://blog.csdn.net/qq_37209293/article/details/88368519 "
            + "https://blog.csdn.net/qq_37209293/article/details/88749184 "
            + "https://blog.csdn.net/qq_37209293/article/details/88733170 "
            + "https://blog.csdn.net/qq_37209293/article/details/88694835 "
            + "https://blog.csdn.net/qq_37209293/article/details/88294792 ";
    public static ArrayList<String> strList=new ArrayList<String>();


    public OpenUrl(){
        strList.add(str1);
        strList.add(str2);
    }

    public static void main(String args[]) {
        // defaultBrowserOpenUrl();
        OpenUrl openUrl=new OpenUrl();
        while(true){
            int i = 0;
            String strUrl = "";
            while (i < 2) {
                strUrl = strList.get(i);
                openChromeBrowser(strUrl, str);
                //每关闭一次浏览器，等待大概30s再重启，太过频繁浏览器会爆炸
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                i++;
            }
            //遍历一次睡一个小时，一天可以跑个二十二二十三次左右
            try {
                Thread.sleep(10*1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //使用指定的浏览器打开
    public static void openChromeBrowser(String start,String stop) {
        // 启用cmd运行firefox的方式来打开网址。
        try {
            Runtime.getRuntime().exec(start);
            try {
                Thread.sleep(1000*30);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Runtime.getRuntime().exec(stop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //使用操作系统默认的浏览器打开
    private static void defaultBrowserOpenUrl() {
        // ...
        try {
            Desktop.getDesktop().browse(new URI("https://blog.csdn.net/qq_37209293/article/details/86481885"));
        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 网址被屏蔽了，手动加网址试一下。
    }

}
