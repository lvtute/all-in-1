package com.hcmute.tlcn2021.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Topic extends BaseEntity {
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="faculty_id")
    private Faculty faculty;

    @JsonIgnore
    @ManyToMany(mappedBy = "topics")
    Set<User> users;

    @JsonIgnore
    @OneToMany(mappedBy="topic")
    private Set<Question> questions;

}
