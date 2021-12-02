package com.hcmute.tlcn2021.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Topic extends BaseEntity {
    private String name;

    @ManyToOne
    @JoinColumn(name="faculty_id")
    private Faculty faculty;

    @ManyToMany(mappedBy = "topics")
    Set<User> users;

}
