package com.hcmute.tlcn2021.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class QuestionTopicKey implements Serializable {
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "topic_id")
    private Long topicId;

    public QuestionTopicKey() {
    }

    public QuestionTopicKey(Long questionId, Long topicId) {
        this.questionId = questionId;
        this.topicId = topicId;
    }
}
