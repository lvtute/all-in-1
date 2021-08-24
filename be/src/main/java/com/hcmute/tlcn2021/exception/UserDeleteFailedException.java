package com.hcmute.tlcn2021.exception;

public class UserDeleteFailedException extends RuntimeException {
    public UserDeleteFailedException(String message) {
        super(message);
    }
}
