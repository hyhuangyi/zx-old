package cn.common.util.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * oss正式包
 * @author Administrator
 *
 */
public class OSSUtil {

	Log log = LogFactory.getLog(OSSUtil.class);
	  // endpoint以杭州为例，其它region请按实际情况填写
	  private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com/";
	  // accessKey
	  private String accessKeyId = "LTAIEPTYQq08Ztra";
	  private String accessKeySecret = "YPZPwAdAuSY46MJL2cnuzP0bBHjPm9";
	  //空间
	  private String bucketName = "qsj-u3dfiles";
	  //文件存储目录
	  private String filedir = "userHeadPortrait/";
	 
	  private OSSClient ossClient;
	 
	  public OSSUtil() {
	    ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	  }
	 
	  /**
	   * 初始化
	   */
	  public void init() {
	    ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	  }
	 
	  /**
	   * 销毁
	   */
	  public void destory() {
	    ossClient.shutdown();
	  }


	/**
	 * 上传图片
	 * @param file
	 * @param fileUrl
	 * @param pictureName
	 * @return
	 */
	public HashMap<String,String> uploading(MultipartFile file, String fileUrl, String pictureName) {
		/*if (file.getSize() > 1024 * 1024) {
		      throw new ImgException("上传图片大小不能超过1M！");
		    }*/
		HashMap<String,String> map=new HashMap<String,String>();
		String originalFilename = file.getOriginalFilename();
		String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
		//String name = passion+ System.currentTimeMillis() + substring;
		String name = pictureName + substring;
		try {
			InputStream inputStream = file.getInputStream();
			this.uploadFile2OSS(inputStream, name,fileUrl);
			String imgUrl = "http://qsj-u3dfiles.oss-cn-hangzhou.aliyuncs.com/"+fileUrl+""+name;
			map.put("imgUrl", imgUrl);
			map.put("imageName", name);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/* 上传图片
	 * @param file 字符串的二进制流
	 * @param fileName 图片名称
	 * @return
	 */
	public String uploading(String fileName,byte[] fileByte) {
		/*if (file.getSize() > 1024 * 1024) {
		      throw new ImgException("上传图片大小不能超过1M！");
		    }*/
		//String name = passion+ System.currentTimeMillis() + substring;
		try {
			InputStream inputStream =new ByteArrayInputStream(fileByte);
			this.uploadFile2OSS(inputStream, fileName,filedir);
			String imgUrl = "http://qsj-u3dfiles.oss-cn-hangzhou.aliyuncs.com/"+filedir+""+fileName;
			return imgUrl;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}




	public HashMap<String,String> uploading(FileInputStream file,String fileUrl,String dename) {
		    /*if (file.getSize() > 1024 * 1024) {
		      throw new ImgException("上传图片大小不能超过1M！");
		    }*/
		   
		   HashMap<String,String> map=new HashMap<String,String>();
		    try {
		      this.uploadFile2OSS(file, dename,fileUrl);
		      String imgUrl = "http://qsj-u3dfiles.oss-cn-hangzhou.aliyuncs.com/"+fileUrl+""+dename;
		      map.put("imgUrl", imgUrl);
		      map.put("imageName", dename);
		      return map;
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		    return map;
	}
	  
	  /**
	   * 上传到OSS服务器  如果同名文件会覆盖服务器上的
	   *
	   * @param instream 文件流
	   * @param fileName 文件名称 包括后缀名
	   * @return 出错返回"" ,唯一MD5数字签名
	   */
	  private String uploadFile2OSS(InputStream instream, String fileName,String fileUrl) {
	    String ret = "";
	    try {
	      //创建上传Object的Metadata 
	      ObjectMetadata objectMetadata = new ObjectMetadata();
	      objectMetadata.setContentLength(instream.available());
	      objectMetadata.setCacheControl("no-cache");
	      objectMetadata.setHeader("Pragma", "no-cache");
	      objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
	      objectMetadata.setContentDisposition("inline;filename=" + fileName);
	      //上传文件
	      PutObjectResult putResult = ossClient.putObject(bucketName, fileUrl + fileName, instream, objectMetadata);
	      ret = putResult.getETag();
	    } catch (IOException e) {
	      log.error(e.getMessage(), e);
	    } finally {
	      try {
	        if (instream != null) {
	          instream.close();
	        }
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	    return ret;
	  }
	  
	  
	  /**
	   * Description: 判断OSS服务文件上传时文件的contentType
	   *
	   * @param FilenameExtension 文件后缀
	   * @return String
	   */
	  private static String getcontentType(String FilenameExtension) {
	    if (FilenameExtension.equalsIgnoreCase("bmp")) {
	      return "image/bmp";
	    }
	    if (FilenameExtension.equalsIgnoreCase("gif")) {
	      return "image/gif";
	    }
	    if (FilenameExtension.equalsIgnoreCase("jpeg") ||
	        FilenameExtension.equalsIgnoreCase("jpg") ||
	        FilenameExtension.equalsIgnoreCase("png")) {
	      return "image/jpeg";
	    }
	    if (FilenameExtension.equalsIgnoreCase("html")) {
	      return "text/html";
	    }
	    if (FilenameExtension.equalsIgnoreCase("txt")) {
	      return "text/plain";
	    }
	    if (FilenameExtension.equalsIgnoreCase("vsd")) {
	      return "application/vnd.visio";
	    }
	    if (FilenameExtension.equalsIgnoreCase(".pdf")) {
		      return "application/pdf";
		    }
	    if (FilenameExtension.equalsIgnoreCase("pptx") ||
	        FilenameExtension.equalsIgnoreCase("ppt")) {
	      return "application/vnd.ms-powerpoint";
	    }
	    if (FilenameExtension.equalsIgnoreCase("docx") ||
	        FilenameExtension.equalsIgnoreCase("doc")) {
	      return "application/msword";
	    }
	    if (FilenameExtension.equalsIgnoreCase("xml")) {
	      return "text/xml";
	    }
	    return "image/jpeg";
	  }
}
