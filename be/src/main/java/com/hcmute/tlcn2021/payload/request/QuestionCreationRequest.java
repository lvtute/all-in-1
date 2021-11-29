package com.hcmute.tlcn2021.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QuestionCreationRequest {

    @NotBlank(message = "Vui lòng điền tiêu đề câu hỏi")
    private String title;

    @NotBlank(message = "Vui lòng điền nội dung câu hỏi")
    private String content;

    @NotBlank(message = "Vui lòng điền tên của bạn")
    private String name;

    @NotBlank(message = "Vui lòng điền địa chỉ email của bạn")
    private String email;

    @NotNull(message = "Vui lòng chọn khoa")
    private Long facultyId;

    @NotNull(message = "Vui lòng chọn chủ đề")
    private Long topicId;

    private Boolean agreeToReceiveEmailNotification;

}
