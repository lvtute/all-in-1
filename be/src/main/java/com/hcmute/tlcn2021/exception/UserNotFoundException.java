package com.hcmute.tlcn2021.exception;

import net.bytebuddy.implementation.bind.annotation.Super;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
