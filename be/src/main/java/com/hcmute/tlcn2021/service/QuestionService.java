package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.payload.request.QuestionCreationRequest;
import com.hcmute.tlcn2021.payload.request.QuestionReplyRequest;
import com.hcmute.tlcn2021.payload.request.QuestionTransferRequest;
import com.hcmute.tlcn2021.payload.response.PaginationResponse;
import com.hcmute.tlcn2021.payload.response.QuestionResponse;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    PaginationResponse findAll(Long facultyId, String searchString, Pageable pageable);

    Long create(QuestionCreationRequest request);

    QuestionResponse findById(Long id);

    PaginationResponse findAllByAdviserId(Boolean noAnswerOnly, String searchString, Pageable pageable);

    QuestionResponse saveAnswer(QuestionReplyRequest questionReplyRequest);

    void delete(Long questionId);

    PaginationResponse findAllByDean(String searchString, Boolean noAnswerOnly, Boolean passedToDean, Pageable pageable);

    void transferQuestion(QuestionTransferRequest questionTransferRequest);
}
