package com.hcmute.tlcn2021.payload.response;

import java.util.List;

public class TopicPaginationResponse {
    List<TopicResponse> content;
    int number;
    int size;
    int totalElements;
    int totalPages;
}
