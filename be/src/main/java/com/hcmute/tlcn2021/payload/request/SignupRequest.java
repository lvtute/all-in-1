package com.hcmute.tlcn2021.payload.request;

import com.hcmute.tlcn2021.enumeration.ERole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotEmpty
    private ERole role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}