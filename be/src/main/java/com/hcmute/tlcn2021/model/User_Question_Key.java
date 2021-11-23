package com.hcmute.tlcn2021.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class User_Question_Key implements Serializable {

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "ques_id")
    private Long ques_id;

    public User_Question_Key() {
    }

    public User_Question_Key(Long user_id, Long ques_id) {
        this.user_id = user_id;
        this.ques_id = ques_id;
    }
}
