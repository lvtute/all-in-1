package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.payload.request.QuestionCreationRequest;
import com.hcmute.tlcn2021.payload.response.QuestionPaginationResponse;
import com.hcmute.tlcn2021.payload.response.QuestionResponse;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    QuestionPaginationResponse findAll(Long facultyId, Pageable pageable);

    Long create(QuestionCreationRequest request);

    QuestionResponse findById(Long id);
}
