package cn.webapp.controller.zx;

import cn.common.util.mail.MailAddress;
import cn.common.util.mail.MailMessageObject;
import cn.common.util.mail.MailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangYi on 2018/9/7
 **/
@Api(description = "邮箱")
@Controller
public class EmailController {
    @Value("${spring.mail.username}")
    private String name;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.host}")
    private String host;
    @Autowired
    private JavaMailSender mailSender;

    @ApiOperation("测试发邮件")
    @RequestMapping(value = "/comm/sendMail",method = RequestMethod.POST)
    @ResponseBody
    public void sendEmail(){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("597505910@qq.com");
        message.setTo("597505910@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");
        mailSender.send(message);
    }
    @ApiOperation("mailUtil测试")
    @PostMapping("/comm/mailUtil")
    @ResponseBody
    public void mail(){
        MailUtil mailUtil=new MailUtil();
        mailUtil.setUsername(name);
        mailUtil.setPassword(password);
        mailUtil.setHost(host);
        MailAddress address=new  MailAddress("zx",name);
        mailUtil.setFrom(address);
        MailMessageObject mailMessageObject=new MailMessageObject();
        mailMessageObject.setContext("邮件内容");
        mailMessageObject.setSubject("邮件主题");
        List<File> fileList=new ArrayList<>();
        fileList.add(new File("E:\\bin\\apache-maven-3.1.1\\README.txt"));
        mailMessageObject.setFileList(fileList);
        List<MailAddress>list=new ArrayList<>();
        list.add(address);
        mailMessageObject.setCc(list);
        mailMessageObject.setTo(list);
        mailUtil.send(mailMessageObject);
    }
}
