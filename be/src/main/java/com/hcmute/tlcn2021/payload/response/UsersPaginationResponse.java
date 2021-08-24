package com.hcmute.tlcn2021.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsersPaginationResponse {
    List<UserDetailsResponse> content;
    int number;
    int size;
    int totalPages;

}
