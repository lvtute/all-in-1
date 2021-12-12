package com.hcmute.tlcn2021.payload.response;

import com.hcmute.tlcn2021.model.Faculty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String role;
    private Faculty faculty;

    public JwtResponse(String accessToken, Long id, String username, String email,
                       String role, Faculty faculty) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.faculty = faculty;
    }
}
