package com.hcmute.tlcn2021.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TopicUpdateRequest {
    @NotNull
    private Long id;

    @NotBlank(message = "Tên chủ đề không được để trống")
    private String name;
}
