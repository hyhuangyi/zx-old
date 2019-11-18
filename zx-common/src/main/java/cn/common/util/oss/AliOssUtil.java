package cn.common.util.oss;

import cn.common.exception.ZXException;
import cn.common.util.generate.IdGenUtils;
import cn.common.util.string.StringUtils;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@Component
public class AliOssUtil {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(AliOssUtil.class);

    /**
     * 阿里云API的内或外网域名
     */
    private static String endpoint;
    /**
     * 阿里云API的密钥Access Key ID
     */
    private static String accessKeyId;
    /**
     * 阿里云API的密钥Access Key Secret
     */
    private static String accessKeySecret;
    /**
     * 阿里云API的bucket名称
     */
    private static String bucketName;

    @Value("${aliyun.accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        AliOssUtil.accessKeyId = accessKeyId;
    }

    @Value("${aliyun.accessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        AliOssUtil.accessKeySecret = accessKeySecret;
    }

    @Value("${aliyun.oss.endpoint}")
    public void setEndpoint(String endpoint) {
        AliOssUtil.endpoint = endpoint;
    }

    @Value("${aliyun.oss.bucketName}")
    public void setBucketName(String bucketName) {
        AliOssUtil.bucketName = bucketName;
    }
    /**
     * 判断是否存在bucketName
     *
     * @return
     */
    private static boolean hasBucket(OSSClient ossClient) {
        return ossClient.doesBucketExist(bucketName);
    }

    /**
     * 创建bucket实例
     */
    private static void creatBucket(OSSClient ossClient) {
        if (!hasBucket(ossClient)) {
            ossClient.createBucket(bucketName);
        }
    }


    /**
     * 获取ossClient
     *
     * @return
     */
    public static OSSClient ossClientInitialization() {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 释放ossClient资源
     */
    private static void ossClientRelease(OSSClient ossClient) {
        ossClient.shutdown();
    }

    /**
     * 获取bucket信息
     */
    private BucketInfo getBucketInfo(OSSClient ossClient) {
        return ossClient.getBucketInfo(bucketName);
    }

    /**
     * 文件上传并返回文件路径
     *
     * @param file   文件
     * @param folder 上传文件夹名称
     * @return
     * @throws IOException
     */
    public static String fileUpload(MultipartFile file, String folder) {
        String url = null;
        OSSClient ossClient = ossClientInitialization();
        creatBucket(ossClient);
        String fileName = getFileName(file.getOriginalFilename());
        // 文件大小
        Long fileSize = file.getSize();
        try {
            // 上传文件流
            InputStream inputStream = file.getInputStream();
            url= uploadFile2OSS(ossClient,inputStream,fileName,fileSize,folder);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String fileUpload(InputStream inputStream, String folder,String fileName,Long fileSize) {
        String url = null;
        OSSClient ossClient = ossClientInitialization();
        creatBucket(ossClient);
        url= uploadFile2OSS(ossClient,inputStream,fileName,fileSize,folder);
        return url;
    }

    private static String uploadFile2OSS(OSSClient ossClient,InputStream instream, String fileName,Long fileSize,String folder) {
        try {
            // 创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            // 上传的文件的长度
            metadata.setContentLength(instream.available());
            // 指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            // 指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            // 指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            // 如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            String ext=fileName.substring(fileName.lastIndexOf("."));
            // 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            //覆盖随机名称
            fileName = IdGenUtils.genImageName();
            metadata.setContentDisposition("filename/filesize=" + fileName + ext+"/" + fileSize + "Byte.");
            ossClient.putObject(bucketName, folder + "/" + fileName+ext, instream, metadata);
            return getUrl(ossClient, folder, fileName+ext);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClientRelease(ossClient);
        }
        return null;
    }

    /**
     * 根据文件名下载文件（获取流）
     * @param objKey
     * @return
     */
    public static ByteArrayOutputStream ossDownLoad(String objKey){
        OSSClient ossClient=ossClientInitialization();
        try {
            ossClient.createBucket(bucketName);
            OSSObject ossObject=ossClient.getObject(bucketName,objKey);
            if(ossObject!=null){
                //ossClientRelease之前转变成字节数组流
               return parse(ossObject.getObjectContent());
            }
            return null;
        } finally {
            //finally是在return语句执行之后，返回之前执行的
            ossClientRelease(ossClient);
        }
    }

    /**
     * 转换成字节流
     * @param in
     * @return
     */
    public static ByteArrayOutputStream parse(InputStream in) {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        try {
            while((ch = in.read()) != -1) {
                swapStream.write(ch);
            }
        } catch (Exception var3) {
            throw new ZXException("流转换异常");
        }
        return swapStream;
    }
    /**
     * 获取附件上传保存到服务器的名称
     *
     * @param fileName 文件原始名称
     * @return
     */
    public static String getFileName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        if (fileName.lastIndexOf(".") > -1) {
            fileName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        } else {
            fileName = "." + fileName.toLowerCase();
        }
        Date date = new Date();
        long t = date.getTime();
        return t + fileName;
    }


    /**
     * 获得url链接
     *
     * @param fileName
     * @return
     */
    public static String getUrl(OSSClient ossClient, String folder, String fileName) {
        // 设置URL过期时间为100年 3600l* 1000*24*365*100
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 100);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, folder + "/" + fileName, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        // 文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)
                || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".png".equalsIgnoreCase(fileExtension)) {
            return "image/png";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        // 默认返回类型
        return "";
    }
}
