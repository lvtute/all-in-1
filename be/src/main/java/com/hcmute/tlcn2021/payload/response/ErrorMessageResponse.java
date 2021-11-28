package com.hcmute.tlcn2021.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessageResponse {
    private String error;

    public ErrorMessageResponse(String message) {
        this.error = message;
    }
}