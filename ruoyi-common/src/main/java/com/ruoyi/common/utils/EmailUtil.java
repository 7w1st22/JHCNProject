package com.ruoyi.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine; // 可选，用于模板渲染

    public static final String FROM = "lule@jianghai.com";

    /**
     * 发送HTML格式邮件
     *
     * @param subject 邮件主题
     * @param htmlContent HTML内容
     * @param to 收件人
     */
    public void sendHtmlEmail(String subject, String htmlContent, String to) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(FROM); // 设置发件人
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true表示内容是HTML

        mailSender.send(message);
    }

    /**
     * 使用Thymeleaf模板发送邮件
     *
     * @param subject 邮件主题
     * @param templateName 模板名称
     * @param variables 模板变量
     * @param to 收件人
     */
    public void sendTemplateEmail(String subject, String templateName,
                                  Map<String, Object> variables, String to) throws MessagingException {
        Context context = new Context();
        if (variables != null) {
            variables.forEach(context::setVariable);
        }

        String htmlContent = templateEngine.process(templateName, context);
        sendHtmlEmail(subject, htmlContent, to);
    }
}
