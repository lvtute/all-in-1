package com.hcmute.tlcn2021.exception;

public class TopicDeleteFailedException extends RuntimeException{
    public TopicDeleteFailedException(String message) {
        super(message);
    }
}
