package com.hcmute.tlcn2021.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ques_topic")
public class QuestionTopic {
    @EmbeddedId
    QuestionTopicKey id;


    @ManyToOne
    @MapsId("question_id")
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @MapsId("topic_id")
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public QuestionTopic() {
    }
}
