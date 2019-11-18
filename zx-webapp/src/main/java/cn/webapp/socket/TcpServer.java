package cn.webapp.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by huangYi on 2018/3/28
 **/
public class TcpServer {

    public TcpServer(){
        try {
            System.out.println(this.getClass().getName()+"初始化中。。。。。");
            ServerSocket serverSocket=new ServerSocket(8888);
            System.out.println(this.getClass().getName()+"初始化完成，等等连接。");
            while(true){
                //等待连接请求
                Socket socket=serverSocket.accept();
                //获取ip和端口
                String remoteIp=socket.getInetAddress().getHostAddress();
                String remotePort=":"+socket.getLocalPort();
                System.out.println("客户端请求IP为 :"+remoteIp+remotePort);
                //获取请求过来的数据
                BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line=in.readLine();
                System.out.println("客户端的请求信息 :"+ line);
                //返回处理信息
                PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
                out.println("服务端已接受请求");
                out.close();
                in.close();
                socket.close();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new TcpServer();
    }
}
