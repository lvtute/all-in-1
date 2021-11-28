package com.hcmute.tlcn2021.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
