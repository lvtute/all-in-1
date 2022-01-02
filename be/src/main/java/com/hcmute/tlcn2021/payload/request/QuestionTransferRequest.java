package com.hcmute.tlcn2021.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QuestionTransferRequest {
    @NotNull(message = "Vui lòng cung cấp id câu hỏi")
    private Long questionId;

    @NotNull(message = "Vui lòng chọn chủ đề")
    private Long topicId;
}
