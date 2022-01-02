package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.service.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Log4j2
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(EmailService.EMAIL_SUBJECT_PREFIX + subject);
            message.setText(text);

            emailSender.send(message);
            log.info("Email was send successfully!");
            log.info("Email content: " + message.toString());
        } catch (MailException exception) {
            log.error("Email sent failed!", exception);
        }
    }

    @Override
    public void sendHtmlMessage(String to, String subject, String htmlFormatContent) {
        try {
            MimeMessage message = emailSender.createMimeMessage();

//            multipart mode (supporting alternative texts, inline elements and attachments) if requested.

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            message.setContent(htmlFormatContent, "text/html; charset=UTF-8");

            helper.setTo(to);

            helper.setSubject(EmailService.EMAIL_SUBJECT_PREFIX + subject);

            emailSender.send(message);
            log.info("Email was send successfully!");
            log.info("Email content: " + message.toString());
        } catch (MailException | MessagingException exception) {
            log.error("Email sent failed!", exception);
        }
    }
}
