package com.hcmute.tlcn2021.payload.request;

import com.hcmute.tlcn2021.enumeration.ERole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserUpdateRequest {

    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    private String faculty;

    @NotNull
    private ERole role;

}
