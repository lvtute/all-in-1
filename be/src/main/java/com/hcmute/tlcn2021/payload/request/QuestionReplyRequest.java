package com.hcmute.tlcn2021.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QuestionReplyRequest {

    @NotNull
    private Long questionId;

    private boolean consultDean;
    private Boolean isPrivate;

    private String answer;

}
