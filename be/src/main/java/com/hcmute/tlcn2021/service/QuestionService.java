package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.payload.request.QuestionCreationRequest;
import com.hcmute.tlcn2021.payload.request.QuestionReplyRequest;
import com.hcmute.tlcn2021.payload.response.PaginationResponse;
import com.hcmute.tlcn2021.payload.response.QuestionResponse;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    PaginationResponse findAll(Long facultyId, Pageable pageable);

    Long create(QuestionCreationRequest request);

    QuestionResponse findById(Long id);

    PaginationResponse findAllByAdviserId(Boolean noAnswerOnly, Pageable pageable);

    QuestionResponse saveAnswer(QuestionReplyRequest questionReplyRequest);

    void delete(Long questionId);
}
