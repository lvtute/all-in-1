package com.hcmute.tlcn2021.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateTopicRequest {
    @NotBlank(message = "Tên chủ đề không được để trống")
    private String name;
}
