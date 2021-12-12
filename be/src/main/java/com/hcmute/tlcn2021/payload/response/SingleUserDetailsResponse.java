package com.hcmute.tlcn2021.payload.response;

import com.hcmute.tlcn2021.model.Faculty;
import com.hcmute.tlcn2021.model.Role;
import com.hcmute.tlcn2021.model.Topic;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SingleUserDetailsResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Faculty faculty;
    private Role role;
    private List<Topic> topics;
}
