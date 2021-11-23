package com.hcmute.tlcn2021.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ques_topic")
public class Ques_topic {
    @EmbeddedId
    Question_Topic_Key id;


    @ManyToOne
    @MapsId("ques_id")
    @JoinColumn(name = "ques_id")
    private Question question;

    @ManyToOne
    @MapsId("topic_id")
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public Ques_topic() {
    }
}
