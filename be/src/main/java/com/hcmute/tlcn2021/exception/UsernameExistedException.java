package com.hcmute.tlcn2021.exception;

public class UsernameExistedException extends RuntimeException{
    public UsernameExistedException(String message) {
        super(message);
    }
}
