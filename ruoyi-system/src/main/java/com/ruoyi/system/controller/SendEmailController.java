package com.ruoyi.system.controller;

import com.ruoyi.common.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/system/email")
public class SendEmailController {


    @Autowired
    private EmailUtil emailUtil;


    @GetMapping("/send")
    public void send(String to)
    {
        String htmlContent = "<h4 style=\"text-align:center;font-weight:500;font-family:&quot;font-size:18px;vertical-align:baseline;color:#232629;\">\n" +
                "\t<strong>Html在线编辑器</strong>\n" +
                "</h4>\n" +
                "<p style=\"font-family:&quot;font-size:14px;vertical-align:baseline;color:#232629;\">\n" +
                "\tHtml在线编<strong><u>辑器提供富文本编辑器,在线文章编辑器,富文本编辑<span style=\"color:#E53333;\">器,Html在</span></u></strong><span style=\"color:#E53333;\">线编辑</span><em><span style=\"color:#E53333;\">器使用<span style=\"background-color:#FF9900;\">，具有高级功能的Html在</span>线编辑器可全屏编辑,Web版Ht</span></em><span style=\"color:#E53333;\">ml在线编辑器在线使用,文章,网站编辑,微信公众号可以在线使用编辑器功能等。</span>\n" +
                "</p>"
                +"<img src=\"https://gw.alicdn.com/bao/uploaded/i3/924441451/O1CN01ZtCGdJ1MabhURT5MQ_!!924441451.jpg_.webp\" placeholder=\"https://gw.alicdn.com/imgextra/i4/O1CN01CYtPWu1MUBqQAUK9D_!!6000000001437-2-tps-2-2.png\" class=\"mainPic--zxTtQs0P\" style=\"padding-top: 0px;\">";
        try {
            emailUtil.sendHtmlEmail("测试HTML邮件", htmlContent, to);
        } catch (MessagingException e) {
            // 处理异常
            e.printStackTrace();
        }
    }

    @GetMapping("/send1")
    public void send1(String to)
    {

        Map<String, Object> variables = new HashMap<>();
        variables.put("title", "测试邮件标题");
        variables.put("content", "这是一封以模板发送的测试邮件内容");
        variables.put("imageUrl", "https://gw.alicdn.com/bao/uploaded/i3/924441451/O1CN01ZtCGdJ1MabhURT5MQ_!!924441451.jpg_.webp");

        try {
            emailUtil.sendTemplateEmail("测试HTML邮件", "email-template", variables,to);
        } catch (MessagingException e) {
            // 处理异常
            e.printStackTrace();
        }
    }



}
