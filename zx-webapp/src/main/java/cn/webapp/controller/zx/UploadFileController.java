package cn.webapp.controller.zx;

import cn.common.exception.ZXException;
import cn.common.pojo.ResultDO;
import cn.common.util.oss.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Api(description = "上传文件")
@Controller
public class UploadFileController {
    @GetMapping("/comm/toUpload")
    @ApiOperation("上传文件")
    public String toUpload(){
        return "upload";
    }
    @GetMapping("/comm/result")
    @ApiOperation("上传文件结果")
    public String result(){
        return "result";
    }

    @ApiOperation("上传")
    @PostMapping("/comm/upload")
    public String upload(@RequestParam("file") MultipartFile file,RedirectAttributes result){
        if(file.isEmpty()){
            result.addFlashAttribute("message", "请不要上传空文件");
            return "redirect:result";
        }

        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd") ;
        String path="D:"+File.separator+ format.format(new Date());
        String filename = file.getOriginalFilename();
        System.out.println(filename);

        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        File targetFile = new File(path, filename);
        // 保存
        try {
            file.transferTo(targetFile);
            result.addFlashAttribute("message",filename+ "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:result";
    }

    @ApiOperation("上传图片")
    @PostMapping("/comm/banner/upload")
    @ResponseBody
    public ResultDO upload(@ApiParam(value = "图片文件", required = true) @RequestParam MultipartFile multipartFile){
        String str="";
        if(multipartFile.isEmpty()){
            throw new ZXException("文件不能为空");
        }
        String fileType=multipartFile.getContentType();
        if("image/jpeg".equalsIgnoreCase(fileType)||"image/png".equalsIgnoreCase(fileType)){
            str= AliOssUtil.fileUpload(multipartFile,"banner");
            if(StringUtils.isNotBlank(str)){
                str=str.substring(0,str.indexOf("?"));
            }
            return new ResultDO("200","成功",str);
        }else {
            throw  new ZXException("文件类型必须是png或jpg");
        }
    }
    @ApiOperation("阿里云文件下载")
    @GetMapping("/comm/oss/down")
    public void ossDownload(String objKey, HttpServletResponse response) throws Exception {
      ByteArrayOutputStream bos= AliOssUtil.ossDownLoad(objKey);
      OutputStream outputStream=response.getOutputStream();
        try {
            response.setContentType("application/force-download");//设置返回的文件类型,强制下载不打开
            response.addHeader("Content-Length", "" );//文件大小
            response.addHeader(
                    "Content-disposition",
                    "attachment;filename="
                            + URLEncoder.encode("测试"+objKey.substring(objKey.lastIndexOf(".")),"utf8"));// 设置头部信息
            if(bos!=null){
               outputStream.write(bos.toByteArray());
            }
        } finally {
            if(outputStream!=null)
                outputStream.close();
        }
    }

}
