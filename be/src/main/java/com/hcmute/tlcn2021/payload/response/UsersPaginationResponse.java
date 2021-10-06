package com.hcmute.tlcn2021.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsersPaginationResponse {
    List<UserInListResponse> content;
    int number;
    int size;
    int totalElements;
    int totalPages;

}
