package com.hcmute.tlcn2021.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UteForumException extends RuntimeException {
    private HttpStatus httpStatus;

    public UteForumException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
