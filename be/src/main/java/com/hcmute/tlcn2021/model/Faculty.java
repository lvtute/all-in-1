package com.hcmute.tlcn2021.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Faculty extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users;

    public Faculty(String name) {
        this.name = name;
    }
}
