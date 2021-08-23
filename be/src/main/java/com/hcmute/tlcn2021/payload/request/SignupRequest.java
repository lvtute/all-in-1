package com.hcmute.tlcn2021.payload.request;

import com.hcmute.tlcn2021.enumeration.ERole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

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

    @NotNull
    private ERole role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    private String faculty;

}
