package com.hcmute.tlcn2021.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_ques")
public class User_ques {
    @EmbeddedId
    User_Question_Key id;


    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("ques_id")
    @JoinColumn(name = "ques_id")
    private Question question;
}
