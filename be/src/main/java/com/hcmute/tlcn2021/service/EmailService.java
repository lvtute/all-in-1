package com.hcmute.tlcn2021.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    String EMAIL_SUBJECT_PREFIX = "[Trang tư vấn sinh viên SPKT] ";
}
