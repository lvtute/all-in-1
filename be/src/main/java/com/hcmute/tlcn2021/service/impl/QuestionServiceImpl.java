package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.exception.UteForumException;
import com.hcmute.tlcn2021.model.Question;
import com.hcmute.tlcn2021.payload.request.QuestionCreationRequest;
import com.hcmute.tlcn2021.payload.response.QuestionPaginationResponse;
import com.hcmute.tlcn2021.payload.response.QuestionResponse;
import com.hcmute.tlcn2021.repository.FacultyRepository;
import com.hcmute.tlcn2021.repository.QuestionRepository;
import com.hcmute.tlcn2021.repository.TopicRepository;
import com.hcmute.tlcn2021.service.QuestionService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Log4j2
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TopicRepository topicRepository;


    @Override
    public QuestionPaginationResponse findAll(Pageable pageable) {
        return convert(questionRepository.findAllByIsDeletedFalse(pageable));
    }

    @Override
    public Long create(QuestionCreationRequest request) {
        Question toBeSavedQuestion = modelMapper.map(request, Question.class);
        toBeSavedQuestion.setFaculty(facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new UteForumException("Khoa không tồn tại.", HttpStatus.NOT_FOUND)));
        toBeSavedQuestion.setTopic(topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new UteForumException("Chủ đề không tồn tại", HttpStatus.NOT_FOUND)));
        if (!toBeSavedQuestion.getTopic().getFaculty().getId().equals(request.getFacultyId())) {
            throw new UteForumException("Chủ đề " + toBeSavedQuestion.getTopic().getName() + " không thuộc khoa " + toBeSavedQuestion.getFaculty().getName(),
                    HttpStatus.BAD_REQUEST);
        }
        Question savedQuestion = questionRepository.save(toBeSavedQuestion);
        log.info("Question saved successfully!");
        return savedQuestion.getId();
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
