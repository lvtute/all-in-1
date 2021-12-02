package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.payload.request.QuestionCreationRequest;
import com.hcmute.tlcn2021.payload.response.QuestionPaginationResponse;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    QuestionPaginationResponse findAll(Pageable pageable);

    Long create(QuestionCreationRequest request);
}
