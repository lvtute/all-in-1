package com.hcmute.tlcn2021.payload.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInListResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String facultyName;
    private String roleName;
}
