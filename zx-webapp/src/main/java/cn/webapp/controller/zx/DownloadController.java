package cn.webapp.controller.zx;

import cn.webapp.aop.annotation.OperateLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * Created by huangYi on 2018/8/12
 **/
@Api(description = "下载文件相关")
@RequestMapping("/comm/download")
@RestController
public class DownloadController {

    private static final Logger logger = Logger.getLogger(DownloadController.class);

    /**
     * 下载,导出
     * @param request
     * @param response
     */
    @RequestMapping(value = "/down",method = RequestMethod.GET)
    @ApiOperation("下载文件")
    @OperateLog(operation = "下载")
    public void download(HttpServletRequest request,HttpServletResponse response){

        FileInputStream fis=null;
        OutputStream fos  = null;
        BufferedInputStream bis  = null;
        BufferedOutputStream bos = null;
        File file=new File("D:/子轩.txt");
        if(file.exists()){

            try {
                fis = new FileInputStream(file); //输入流
                fos=response.getOutputStream(); // 获取字节流
                bis=new BufferedInputStream(fis);//输入缓冲流
                bos=new BufferedOutputStream(fos);//输出缓冲流
                //解决中文乱码  //http header头要求其内容必须为iso8859-1编码  //utf8改成gbk兼容ie
                String name=new String(file.getName().getBytes("gbk"),"iso8859-1");
                //方法二
                String name1= URLEncoder.encode(file.getName(),"utf8");

                response.setContentType("application/force-download");//设置返回的文件类型,强制下载不打开
                response.addHeader("Content-Length", "" + file.length());//文件大小
                response.addHeader(
                        "Content-disposition",
                        "attachment;filename="
                                + name);// 设置头部信息
                /*
                byte data[] = new byte[4096];// 缓冲字节数
                int size = 0;
                size = bis.read(data);
                while (size != -1) {
                    bos.write(data, 0, size);
                    size = bis.read(data);
                }*/

                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                // 开始向网络传输文件流
                while ((bytesRead = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
                bis.close();
                bos.flush();// 清空输出缓冲流
                bos.close();
                fos.close();

            } catch (IOException e) {
                logger.error("下载出错",e);
            }
        }
    }
}
