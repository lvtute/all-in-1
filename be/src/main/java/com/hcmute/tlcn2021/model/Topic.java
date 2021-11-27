package com.hcmute.tlcn2021.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Entity
@Table
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name_topic")
    private String nameTopic;

    @NotBlank
    @Column(name = "is_deleted",columnDefinition = "boolean default false")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "topic")
    private Set<QuestionTopic> topicQuesList;
}
