package com.hcmute.tlcn2021.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class FacultyUpdateRequest {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

//    public String getName() {
//        return name;
//    }
}
