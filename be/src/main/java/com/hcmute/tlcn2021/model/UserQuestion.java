package com.hcmute.tlcn2021.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_ques")
public class UserQuestion {
    @EmbeddedId
    UserQuestionKey id;


    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("question_id")
    @JoinColumn(name = "question_id")
    private Question question;
}
