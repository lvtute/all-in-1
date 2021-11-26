package com.hcmute.tlcn2021.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Column(name = "user_name")
    private String userName;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    @Column(name = "write_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date writeDate;

    @NotBlank
    @Column(name = "modify_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date modifyDate;

    @NotBlank
    @Column(name = "is_answered")
    private boolean isAnswered;

    @NotBlank
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @OneToMany(mappedBy="question", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Answer> answer;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    private Set<QuestionTopic> quesTopicList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    private Set<UserQuestion> quesUserList;

}
