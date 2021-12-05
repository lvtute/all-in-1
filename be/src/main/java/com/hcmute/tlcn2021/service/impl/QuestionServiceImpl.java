package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.exception.UteForumException;
import com.hcmute.tlcn2021.model.Question;
import com.hcmute.tlcn2021.payload.request.QuestionCreationRequest;
import com.hcmute.tlcn2021.payload.response.QuestionPaginationResponse;
import com.hcmute.tlcn2021.payload.response.QuestionResponse;
import com.hcmute.tlcn2021.repository.FacultyRepository;
import com.hcmute.tlcn2021.repository.QuestionRepository;
import com.hcmute.tlcn2021.repository.TopicRepository;
import com.hcmute.tlcn2021.repository.UserRepository;
import com.hcmute.tlcn2021.service.QuestionService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    public QuestionPaginationResponse findAll(Long facultyId, Pageable pageable) {
        if (ObjectUtils.isEmpty(facultyId) || facultyId == 0) {
            return convert(questionRepository.findAllByIsDeletedFalse(pageable));
        }
        return convert(questionRepository.findAllByIsDeletedFalseAndFaculty_IdEquals(facultyId, pageable));
    }

    @Transactional
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

        Optional<Long> optionalAdviserId = userRepository.findAdviserIdForAssigningQuestionByTopicId(toBeSavedQuestion.getTopic().getId());
        Long adviserId = optionalAdviserId.orElseGet(() -> userRepository.findAdviserIdForAssigningQuestionByFaculty(toBeSavedQuestion.getFaculty().getId())
                .orElseThrow(() -> new UteForumException("Không tìm được tư vấn viên phù hợp", HttpStatus.NOT_FOUND)));

        toBeSavedQuestion.setUser(userRepository.findById(adviserId)
                .orElseThrow(() -> new UteForumException("Không tìm được tư vấn viên phù hợp", HttpStatus.NOT_FOUND)));

        Question savedQuestion = questionRepository.save(toBeSavedQuestion);
        log.info("Question saved successfully!");
        return savedQuestion.getId();
    }

    @Override
    public QuestionResponse findById(Long id) {
        Question foundQuestion = questionRepository.findById(id).orElseThrow(() -> new UteForumException("Không tìm thấy câu hỏi", HttpStatus.NOT_FOUND));
        increaseQuestionViews(foundQuestion);
        return convert(foundQuestion);
    }

    private void increaseQuestionViews(Question question) {
        question.setViews(question.getViews() + 1);
        questionRepository.save(question);

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
