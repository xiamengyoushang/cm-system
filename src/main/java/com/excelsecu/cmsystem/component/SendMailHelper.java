package com.excelsecu.cmsystem.component;

import com.excelsecu.cmsystem.common.exceptions.ArgsException;
import com.excelsecu.cmsystem.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * 发送邮件信息
 * https://www.cnblogs.com/sun2020/p/13961554.html
 * */
@Component
public class SendMailHelper {

    @Value("${spring.mail.username}")
    String adminMail = "";

    @Autowired
    JavaMailSender javaMailSender;

    public void sendSimpleMail(String account, String content) throws ArgsException{
        if (Utils.isMail(adminMail)&&Utils.isMail(account)) {} else {
            throw new ArgsException("发送邮件-邮箱格式错误");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("后台管理系统-验证码");
        message.setFrom(adminMail);
        message.setTo(account);
        // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
        // message.setTo("xxx@xxx.com","xxx@xxx.com");
        // 设置邮件抄送人，可以有多个抄送人
        // message.setCc("xxx@xxx.com");
        // 设置隐秘抄送人，可以有多个
        // message.setBcc("xxx@xxx.com");
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的内容
        message.setText(content);
        // 发送邮件
        javaMailSender.send(message);
    }

    public void sendAttachFileMail(String account, String content) throws MessagingException, ArgsException {
        if (Utils.isMail(adminMail)&&Utils.isMail(account)) {} else {
            throw new ArgsException("发送邮件-邮箱格式错误");
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // 构建一个带附件的邮件对象
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setSubject("后台管理系统-附件");
        helper.setFrom(adminMail);
        helper.setTo(account);
        //helper.setCc("xxx@xxx.com");
        //helper.setBcc("xxx@xxx.com");
        helper.setSentDate(new Date());
        helper.setText(content);
        // 第一个参数是自定义的名称，后缀需要加上，第二个参数是文件的位置
        helper.addAttachment("xxx.txt",new File("/xxx/xxx.txt"));
        javaMailSender.send(mimeMessage);
    }

    public void sendImgResMail(String account) throws MessagingException, ArgsException {
        if (Utils.isMail(adminMail)&&Utils.isMail(account)) {} else {
            throw new ArgsException("发送邮件-邮箱格式错误");
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("后台管理系统-资源");
        helper.setFrom(adminMail);
        helper.setTo(account);
        //helper.setCc("xxx@xxx.com");
        //helper.setBcc("xxx@xxx.com");
        helper.setSentDate(new Date());
        // src='cid:p01' 占位符写法 ，第二个参数true表示这是一个html文本
        helper.setText("<p>hello 大家好，这是一封测试邮件，这封邮件包含两种图片，分别如下</p><p>第一张图片：</p><img src='cid:p01'/><p>第二张图片：</p><img src='cid:p02'/>",true);
        // 第一个参数指的是html中占位符的名字，第二个参数就是文件的位置
        helper.addInline("p01",new FileSystemResource(new File("/xxx/xxx1.png")));
        helper.addInline("p02",new FileSystemResource(new File("/xxx/xxx2.jpg")));
        javaMailSender.send(mimeMessage);
    }

}
