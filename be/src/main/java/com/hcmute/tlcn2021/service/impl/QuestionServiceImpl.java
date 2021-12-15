package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.config.service.AuthenticationFacade;
import com.hcmute.tlcn2021.config.service.UserDetailsImpl;
import com.hcmute.tlcn2021.enumeration.ERole;
import com.hcmute.tlcn2021.exception.UteForumException;
import com.hcmute.tlcn2021.model.Faculty;
import com.hcmute.tlcn2021.model.Question;
import com.hcmute.tlcn2021.model.User;
import com.hcmute.tlcn2021.payload.request.QuestionCreationRequest;
import com.hcmute.tlcn2021.payload.request.QuestionReplyRequest;
import com.hcmute.tlcn2021.payload.response.PaginationResponse;
import com.hcmute.tlcn2021.payload.response.QuestionResponse;
import com.hcmute.tlcn2021.repository.FacultyRepository;
import com.hcmute.tlcn2021.repository.QuestionRepository;
import com.hcmute.tlcn2021.repository.TopicRepository;
import com.hcmute.tlcn2021.repository.UserRepository;
import com.hcmute.tlcn2021.service.EmailService;
import com.hcmute.tlcn2021.service.QuestionService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
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

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private EmailService emailService;

    @Value("${fe.path.question.home-detail}")
    private String feQuestionHomeDetailPath;

    @Value("${fe.path.question.replier}")
    private String feQuestionReplierPath;

    @Override
    public PaginationResponse findAll(Long facultyId, Pageable pageable) {
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

        toBeSavedQuestion.setAdviser(userRepository.findById(adviserId)
                .orElseThrow(() -> new UteForumException("Không tìm được tư vấn viên phù hợp", HttpStatus.NOT_FOUND)));

        Question savedQuestion = questionRepository.save(toBeSavedQuestion);
        log.info("Question saved successfully!");

        // send email to notify assigned adviser
        StringBuilder message = new StringBuilder();
        message.append("Chào ").append(savedQuestion.getAdviser().getFullName()).append(",\n");
        message.append("Với vai trò Tư vấn viên, bạn được giao 1 câu hỏi mới.").append("\n");
        message.append("Mời bạn xem và trả lời tại địa chỉ ").append(feQuestionReplierPath).append(savedQuestion.getId()).append("\n");
        emailService.sendSimpleMessage(savedQuestion.getAdviser().getEmail(), EmailService.EMAIL_SUBJECT_PREFIX + "Mời trả lời câu hỏi", message.toString());

        return savedQuestion.getId();


    }

    @Override
    public QuestionResponse findById(Long id) {
        Question foundQuestion = questionRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new UteForumException("Không tìm thấy câu hỏi", HttpStatus.NOT_FOUND));
        increaseQuestionViews(foundQuestion);
        return convert(foundQuestion);
    }

    @Override
    @Secured({"ROLE_DEAN", "ROLE_ADVISER"})
    public PaginationResponse findAllByAdviserId(Boolean noAnswerOnly, Pageable pageable) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();

        if (noAnswerOnly.equals(true)) {
            return convert(questionRepository.findAllByIsDeletedFalseAndAdviser_IdEqualsAndAnswerNull(userDetails.getId(), pageable));
        }

        return convert(questionRepository.findAllByIsDeletedFalseAndAdviser_IdEquals(userDetails.getId(), pageable));
    }

    @Secured({"ROLE_DEAN", "ROLE_ADVISER"})
    @Transactional
    @Override
    public QuestionResponse saveAnswer(QuestionReplyRequest questionReplyRequest) {
        Question question = questionRepository.findById(questionReplyRequest.getQuestionId())
                .orElseThrow(() -> new UteForumException("Câu hỏi không tồn tài", HttpStatus.NOT_FOUND));

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();

        if (question.getAdviser() != null && userDetails.getAuthorities().stream().noneMatch(r -> r.getAuthority().equals(ERole.ROLE_DEAN.toString()))
                && !userDetails.getId().equals(question.getAdviser().getId())) {
            throw new UteForumException("Bạn không phải là tư vấn viên được giao của câu hỏi này", HttpStatus.FORBIDDEN);
        }
        if (userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(ERole.ROLE_DEAN.toString()))) {
            question.setApprovedByDean(true);
        }
        question.setAnswer(questionReplyRequest.getAnswer());

        QuestionResponse result = convert(questionRepository.save(question));

        // send email to the question creator
        if (question.getAgreeToReceiveEmailNotification().equals(Boolean.TRUE)) {
            StringBuilder message = new StringBuilder();
            message.append("Chào ").append(question.getName()).append(",\n");
            message.append("Câu hỏi của bạn với tiêu đề: ").append(question.getTitle()).append(" đã được trả lời.\n");
            message.append("Mời bạn xem tại địa chỉ ").append(feQuestionHomeDetailPath).append(question.getId());
            emailService.sendSimpleMessage(question.getEmail(), EmailService.EMAIL_SUBJECT_PREFIX + "Câu hỏi đã được trả lời", message.toString());
        }

        // send email to dean for approval
        if (questionReplyRequest.isConsultDean()) {

            User dean = userRepository.findByRole_NameEqualsAndFaculty_IdEqualsAndIsDeletedFalse(ERole.ROLE_DEAN, question.getFaculty().getId())
                    .orElseThrow(() -> new UteForumException("Không tìm được Trưởng khoa", HttpStatus.INTERNAL_SERVER_ERROR));
            if (ObjectUtils.isEmpty(dean.getEmail())) {
                throw new UteForumException("Trưởng khoa chưa cung cấp email", HttpStatus.NOT_FOUND);
            }

            StringBuilder message = new StringBuilder();
            message.append("Chào ").append(dean.getFullName()).append(",\n");
            message.append("Với vai trò Trưởng khoa, bạn nhận được một đề nghị phê duyệt câu trả lời").append("\n");
            message.append("Mời bạn xem tại địa chỉ ").append(feQuestionReplierPath).append(question.getId()).append("\n");
            emailService.sendSimpleMessage(dean.getEmail(), EmailService.EMAIL_SUBJECT_PREFIX + "Thư xin phê duyệt câu trả lời", message.toString());
        }

        return result;
    }

    @Secured({"ROLE_DEAN", "ROLE_ADVISER"})
    @Override
    @Transactional
    public void delete(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new UteForumException("Câu hỏi không tồn tài", HttpStatus.NOT_FOUND));

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();
        if (question.getAdviser() != null && userDetails.getAuthorities().stream().noneMatch(r -> r.getAuthority().equals(ERole.ROLE_DEAN.toString()))
                && !userDetails.getId().equals(question.getAdviser().getId())) {
            // any body can delete no-adviser questions, deans can delete any question

            throw new UteForumException("Bạn không phải là tư vấn viên được giao của câu hỏi này", HttpStatus.FORBIDDEN);
        }

        int updatedRows = questionRepository.softDelete(questionId);
        if (updatedRows == 0) {
            throw new UteForumException("Xóa không thành công", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (question.getAgreeToReceiveEmailNotification().equals(Boolean.TRUE)) {
            // send email notification about question is deleted
            String message = "Câu hỏi của bạn với tiêu đề:\n" + question.getTitle() + "\n đã bị xóa.";
            emailService.sendSimpleMessage(question.getEmail(), EmailService.EMAIL_SUBJECT_PREFIX + "Câu hỏi đã bị xóa", message);
        }
    }

    @Secured({"ROLE_DEAN"})
    @Override
    public PaginationResponse findAllByDean(Boolean noAnswerOnly, Pageable pageable) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();
        Faculty faculty = userDetails.getFaculty();

        if (noAnswerOnly.equals(true)) {
            return convert(questionRepository.findAllByIsDeletedFalseAndFaculty_IdEqualsAndAnswerNull(faculty.getId(), pageable));
        }

        return convert(questionRepository.findAllByIsDeletedFalseAndFaculty_IdEquals(faculty.getId(), pageable));
    }

    private void increaseQuestionViews(Question question) {
        question.setViews(question.getViews() + 1);
        questionRepository.save(question);
    }

    private PaginationResponse convert(Page<Question> questionPage) {
        PaginationResponse result = modelMapper.map(questionPage, PaginationResponse.class);
        result.setContent(questionPage.getContent().stream()
                .map(this::convert).collect(Collectors.toList()));
        return result;
    }

    private QuestionResponse convert(Question question) {

        return modelMapper.map(question, QuestionResponse.class);
    }
}
