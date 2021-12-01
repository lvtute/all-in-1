package com.hcmute.tlcn2021.payload.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FacultyPaginationResponse {
    List<FacultyResponse> content;
    int number;
    int size;
    int totalElements;
    int totalPages;
}
