package com.hcmute.tlcn2021.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class UserQuestionKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "question_id")
    private Long questionId;

    public UserQuestionKey() {
    }

    public UserQuestionKey(Long userId, Long quesId) {
        this.userId = userId;
        this.questionId = quesId;
    }
}
