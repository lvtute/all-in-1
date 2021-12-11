package com.hcmute.tlcn2021.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailTestRequest {
    private String receiver;
    private String subject;
    private String text;
}
