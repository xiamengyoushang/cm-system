package com.excelsecu.cmsystem.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 发送邮件信息
 * https://www.cnblogs.com/sun2020/p/13961554.html
 * */
@Component
public class SendMailMessage {

    @Value("${spring.mail.username}")
    String adminMail = "";

    @Autowired
    JavaMailSender javaMailSender;

    public void sendMailVerifyCode(String account, String code){
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
        message.setText(code);
        // 发送邮件
        javaMailSender.send(message);
    }

}
