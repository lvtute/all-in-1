package com.hcmute.tlcn2021.exception;

public class TopicNotFoundException extends RuntimeException{
    public TopicNotFoundException(String message) {
        super(message);
    }
}
