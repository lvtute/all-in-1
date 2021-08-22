package com.hcmute.tlcn2021.payload.request;

import com.hcmute.tlcn2021.enumeration.ERole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserUpdateRequest {

    @NotEmpty
    private Long id;

    @NotEmpty
    @Size(max = 50)
    @Email
    private String email;

    @NotEmpty
    private ERole role;

}
