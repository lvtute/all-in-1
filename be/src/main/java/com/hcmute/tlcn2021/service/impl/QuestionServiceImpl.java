package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.model.Question;
import com.hcmute.tlcn2021.payload.response.QuestionPaginationResponse;
import com.hcmute.tlcn2021.payload.response.QuestionResponse;
import com.hcmute.tlcn2021.repository.QuestionRepository;
import com.hcmute.tlcn2021.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public QuestionPaginationResponse findAll(Pageable pageable) {
        return convert(questionRepository.findAllByIsDeletedFalse(pageable));
    }

    private QuestionPaginationResponse convert(Page<Question> questionPage) {
        QuestionPaginationResponse result = modelMapper.map(questionPage, QuestionPaginationResponse.class);
        result.setContent(questionPage.getContent().stream()
                .map(this::convert).collect(Collectors.toList()));
        return result;
    }

    private QuestionResponse convert(Question question) {

        return modelMapper.map(question, QuestionResponse.class);
    }
}
