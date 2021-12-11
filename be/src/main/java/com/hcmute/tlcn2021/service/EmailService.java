package com.hcmute.tlcn2021.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
