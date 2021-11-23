package com.hcmute.tlcn2021.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Question_Topic_Key implements Serializable {
    @Column(name = "ques_id")
    private Long ques_id;

    @Column(name = "topic_id")
    private Long topic_id;

    public Question_Topic_Key() {
    }

    public Question_Topic_Key(Long ques_id, Long topic_id) {
        this.ques_id = ques_id;
        this.topic_id = topic_id;
    }
}
