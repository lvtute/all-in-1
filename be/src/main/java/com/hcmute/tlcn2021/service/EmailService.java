package com.hcmute.tlcn2021.service;

public interface EmailService {

    String EMAIL_SUBJECT_PREFIX = "[Trang tư vấn sinh viên SPKT] ";

    void sendSimpleMessage(String to, String subject, String text);

    void sendHtmlMessage(String to, String subject, String htmlFormatContent);

}
