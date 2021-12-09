package com.hcmute.tlcn2021.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy="faculty", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users;
    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    public Faculty() {
    }

    public Faculty(String name) {
        this.name = name;
    }
}
