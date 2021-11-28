package com.hcmute.tlcn2021.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Entity

public class Topic extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="faculty_id")
    private Faculty faculty;


}
