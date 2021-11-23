package com.hcmute.tlcn2021.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "unique_username_constraint",columnNames = "username"),
        @UniqueConstraint(name = "unique_email_constraint", columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name="faculty_id")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Answer> answer;

//    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Set<Question> question;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<User_ques> userQuesList;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}