package com.hcmute.tlcn2021.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserUpdateRequest {

    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private long facultyId;

    private long roleId;

    @NotBlank
    private String fullName;

}
