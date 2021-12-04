package com.hcmute.tlcn2021.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {

    private long id;

    private String title;

    private String content;

    private String name;

    private String email;

    private String facultyName;

    private String topicName;

    private String createdDate;

    private String updatedDate;

    private int views;

    private String answer;

    private String userFullName;

    private boolean approvedByDean;
}
