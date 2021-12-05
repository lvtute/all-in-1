package com.hcmute.tlcn2021.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcmute.tlcn2021.enumeration.ERole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private ERole name;

//    @Column(name = "is_deleted")
//    private boolean is_deleted;

    @OneToMany(mappedBy="role", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users;

    public Role(ERole eRole) {
        this.name = eRole;
    }

}