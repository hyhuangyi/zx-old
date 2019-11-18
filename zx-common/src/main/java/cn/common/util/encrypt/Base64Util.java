package cn.common.util.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.util.UUID;

/**
 * @author zhangqw
 * @version V1.0
 * @date 2017-02-13 16:04
 */
public class Base64Util {
    /**
     * 日志 
     */
    private static Log logger = LogFactory.getLog(Base64Util.class);

    /**
     * 不需要重复创建对象Base解析对象
     */
    public static final Base64 BASE_64 = new Base64();
    
    /**
     * 将文件转成base64 字符串 
     * @param file 文件 
     * @return encodedFileString
     * @throws Exception
     */
    public static String encodeImgageToBase64(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        // 取得文件大小  
        long length = file.length();
        // 根据大小创建字节数组  
        byte[] buffer = new byte[(int) length];
        
        inputFile.read(buffer);
        inputFile.close();
        //base64转码 new String((new Base64()).encode(buffer))  
        String encodedFileString = Base64.encodeBase64String(buffer);
        return encodedFileString;
    }
    
    /**
     * 将本地图片进行Base64位编码 
     *
     * @param bufferedImage    
     * @return
     */
    public static String encodeImgageToBase64(BufferedImage bufferedImage) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (MalformedURLException e1) {
            logger.info(e1);
        } catch (IOException e) {
            logger.info(e);
        }
        // 对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串  
    }
    
    /**
     * 将base64字符解码保存文件 
     * @param base64Code  解码 
     * @param targetPath 要存的地址 
     * @return
     */
    
    public static String decoderBase64File(String base64Code, String targetPath) {
        File file = new File(targetPath);
        try {
            if (file.exists()) {
                logger.error(targetPath + " 文件已经存在，不能转换为文件");
                return null;
            } else {
                boolean createNewFile = file.createNewFile();
                if (createNewFile) {
                    logger.info("文件创建成功！");
                } else {
                    logger.info("文件创建遇到问题。");
                }
            }
            byte[] buffer = BASE_64.decode(base64Code);
            FileOutputStream out = new FileOutputStream(targetPath);
            out.write(buffer);
            out.close();
            logger.info("文件保存成功！");
        } catch (Exception e) {
            logger.error("文件base64编码转换失败！",e);
            targetPath = "";
        }
        
        return targetPath;
    }
    /**
     * 将字符串转换成Base64编码 
     * @param tagertStr 要转换的字符串 
     * @return
     */
    public static String convert(String tagertStr) {
        byte[] value;
        try {
            value = tagertStr.getBytes("UTF-8");
            return new String(Base64.encodeBase64(value),"UTF-8");
        } catch (Exception e) {
            logger.error("字符串base64编码转换失败！" + e);
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        String bse64Code = "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";
//        String bse64Code = "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";
        String resFile = "f:/" + UUID.randomUUID() + ".jpg";
        decoderBase64File(bse64Code,resFile);
        /*BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(new Base64().decode(bse64Code)));
        System.out.println(bufImg == null);*/
        /*BufferedImage bi = ImageIO.read(new File(resFile));
        if(bi == null){
            System.out.println("此文件不为图片文件");
        }*/
    }

}
