package com.hcmute.tlcn2021.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class QuestionCreationRequest {

    @NotBlank(message = "Vui lòng điền tiêu đề câu hỏi")
    @Size(min = 3, max = 200, message = "Câu hỏi có độ dài từ 3-200 ký tự")
    private String title;

    @Size(min = 1, max = 500, message = "Nội dung quá dài")
    @NotBlank(message = "Vui lòng điền nội dung câu hỏi")
    private String content;

    @NotBlank(message = "Vui lòng điền tên của bạn")
    @Size(max = 200, message = "Tên không được phép dài quá 50 ký tự")
    private String name;

    @NotBlank(message = "Vui lòng điền địa chỉ email của bạn")
    @Email(message = "Địa chỉ email không hợp lệ")
    @Size(max = 200, message = "Email không được phép dài quá 50 ký tự")
    private String email;

    @NotNull(message = "Vui lòng chọn khoa")
    private Long facultyId;

    @NotNull(message = "Vui lòng chọn chủ đề")
    private Long topicId;

    private Boolean agreeToReceiveEmailNotification;

}
