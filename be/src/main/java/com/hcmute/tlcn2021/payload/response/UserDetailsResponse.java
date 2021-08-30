package com.hcmute.tlcn2021.payload.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String facultyName;
    private Set<String> roleNames;
}
