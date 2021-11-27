package com.hcmute.tlcn2021.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
@Table
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @NotBlank
    @Column(name = "is_deleted",columnDefinition = "boolean default false")
    private boolean isDeleted;

    @NotBlank
    @Column(name = "write_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date writeDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;


    public Answer() {
    }

    public Answer(Long id, String content, boolean isDeleted, Date writeDate) {
        this.id = id;
        this.content = content;
        this.isDeleted = isDeleted;
        this.writeDate = writeDate;
    }
}
