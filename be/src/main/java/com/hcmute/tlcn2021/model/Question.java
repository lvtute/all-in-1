package com.hcmute.tlcn2021.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Question extends BaseEntity{

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String name;

    private String email;

    @ManyToOne
    @JoinColumn(name="faculty_id")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name="topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name="advisor_id")
    private User user;

    private Boolean agreeToReceiveEmailNotification;

}
