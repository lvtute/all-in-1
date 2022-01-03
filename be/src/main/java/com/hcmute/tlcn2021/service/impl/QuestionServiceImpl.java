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
import com.hcmute.tlcn2021.payload.request.QuestionTransferRequest;
import com.hcmute.tlcn2021.payload.response.PaginationResponse;
import com.hcmute.tlcn2021.payload.response.QuestionResponse;
import com.hcmute.tlcn2021.repository.FacultyRepository;
import com.hcmute.tlcn2021.repository.QuestionRepository;
import com.hcmute.tlcn2021.repository.TopicRepository;
import com.hcmute.tlcn2021.repository.UserRepository;
import com.hcmute.tlcn2021.service.EmailService;
import com.hcmute.tlcn2021.service.QuestionService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.BooleanUtils;
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

import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import com.hcmute.tlcn2021.enumeration.QuestionStatus;

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

    @Autowired
    private ExecutorService executorService;

    @Override
    public PaginationResponse findAll(Long facultyId, String searchString, Pageable pageable) {

        searchString = standardizeSearchString(searchString);

        if (ObjectUtils.isEmpty(facultyId) || facultyId == 0) {
            Page<Question> queryResult = questionRepository.findByIsPrivateFalseAndSearchString(searchString, pageable);
            return convert(queryResult);
        }
        return convert(questionRepository.findByFaculty_IdEqualsAndIsPrivateFalseAndSearchString(searchString, facultyId, pageable));
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
        Runnable sendEmail = () -> {
            String message = "<p>" + "Chào " + savedQuestion.getAdviser().getFullName() + "</p>" +
                    "<p>" + "Với vai trò Tư vấn viên, bạn được giao 1 câu hỏi mới." + "</p>" +
                    "<p>" + MessageFormat.format("Mời bạn xem và trả lời tại <a href=\"{0}\">{0}</a>", feQuestionReplierPath + savedQuestion.getId()) + "</p>";
            emailService.sendHtmlMessage(savedQuestion.getAdviser().getEmail(), "Mời trả lời câu hỏi", message);

        };
        executorService.execute(sendEmail);

        return savedQuestion.getId();

    }

    @Secured({"ROLE_DEAN", "ROLE_ADVISER"})
    @Override
    public QuestionResponse findByIdIncludingPrivate(Long id) {

        Question foundQuestion = questionRepository.findById(id).orElseThrow(() -> new UteForumException("Không tìm thấy câu hỏi", HttpStatus.NOT_FOUND));

        increaseQuestionViews(foundQuestion);
        return convert(foundQuestion);
    }


    @Override
    public QuestionResponse findById(Long id) {

        Question foundQuestion = questionRepository.findByIdAndIsPrivateFalse(id).orElseThrow(() -> new UteForumException("Không tìm thấy câu hỏi", HttpStatus.NOT_FOUND));

        increaseQuestionViews(foundQuestion);
        return convert(foundQuestion);
    }

    @Override
    @Secured({"ROLE_DEAN", "ROLE_ADVISER"})
    public PaginationResponse findAllByAdviserId(Boolean noAnswerOnly, String searchString, Pageable pageable) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();

        searchString = standardizeSearchString(searchString);

        if (noAnswerOnly.equals(true)) {
            return convert(questionRepository.findByAnswerNullAndAdviserIdEqualsAndSearchString(userDetails.getId(), searchString, pageable));
        }

        return convert(questionRepository.findByAnswerNotNullAndAdviserIdEqualsAndSearchString(userDetails.getId(), searchString, pageable));
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
        question.setAnswer(questionReplyRequest.getAnswer());

        if (userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(ERole.ROLE_DEAN.toString()))) {
            question.setApprovedByDean(true);
            question.setStatus(QuestionStatus.NONE);
        }

        if (questionReplyRequest.isConsultDean()) {
            question.setApprovedByDean(false);
            question.setStatus(QuestionStatus.PASSED_TO_DEAN);
        }
        question.setPrivate(BooleanUtils.isTrue(questionReplyRequest.getIsPrivate()));

        Question saved = questionRepository.save(question);
        QuestionResponse result = convert(saved);

//      send email to the question creator

        executorService.execute(() -> {
            StringBuilder message = new StringBuilder();
            message.append("<p>").append("Chào ").append(question.getName()).append("</p>");
            message.append("<p>").append("Câu hỏi của bạn với tiêu đề: ").append(question.getTitle()).append(" đã được trả lời bởi ").append(result.getAdviserFullName()).append("</p>");
            message.append("<p>").append(result.getAnswer()).append("</p>");
            if (!saved.isPrivate()) {
                message.append("<p>").append(MessageFormat.format("Mời bạn xem tại địa chỉ <a href=\"{0}\">{0}</a>", feQuestionHomeDetailPath + question.getId())).append("</p>");
            } else {
                message.append("<p>").append("Tư vấn viên đã đánh dấu câu hỏi này là riêng tư. Vì vậy bạn chỉ mình bạn mới có thể xem câu trả lời tại đây, và câu hỏi sẽ không xuất hiện trên web tư vấn.").append("</p>");
            }
            emailService.sendHtmlMessage(question.getEmail(), "Câu hỏi của bạn đã được trả lời", message.toString());

            // send email to dean for approval
            if (questionReplyRequest.isConsultDean()) {

                User dean = userRepository.findByRole_NameEqualsAndFaculty_IdEqualsAndIsDeletedFalse(ERole.ROLE_DEAN, question.getFaculty().getId())
                        .orElseThrow(() -> new UteForumException("Không tìm được Trưởng ban tư vấn", HttpStatus.INTERNAL_SERVER_ERROR));
                if (ObjectUtils.isEmpty(dean.getEmail())) {
                    throw new UteForumException("Trưởng ban tư vấn chưa cung cấp email", HttpStatus.NOT_FOUND);
                }

                message = new StringBuilder();
                message.append("<p>").append("Chào ").append(dean.getFullName()).append("</p>");
                message.append("<p>").append("Với vai trò Trưởng ban tư vấn, bạn nhận được một đề nghị phê duyệt câu trả lời").append("</p>");
                message.append("<p>").append(MessageFormat.format("Mời bạn xem tại địa chỉ <a href=\"{0}\">{0}</a>", feQuestionReplierPath + question.getId())).append("</p>");
                emailService.sendHtmlMessage(dean.getEmail(), "Thư xin phê duyệt câu trả lời", message.toString());
            }
        });


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

        questionRepository.deleteById(questionId);

        // send email notification about question is deleted
       executorService.execute(() -> {
            String message = "Câu hỏi của bạn với tiêu đề:\n" + question.getTitle() + "\n đã bị xóa.";
            emailService.sendSimpleMessage(question.getEmail(), "Câu hỏi đã bị xóa", message);
        });

    }

    @Secured({"ROLE_DEAN"})
    @Override
    public PaginationResponse findAllByDean(String searchString, Boolean noAnswerOnly, Boolean passedToDean, Pageable pageable) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();
        Faculty faculty = userDetails.getFaculty();

        searchString = standardizeSearchString(searchString);

        if (BooleanUtils.isTrue(passedToDean)) {
            return convert(questionRepository.findByFaculty_IdEqualsAndPassedToDeanAndSearchString(searchString, faculty.getId(), pageable));
        }

        if (noAnswerOnly.equals(true)) {
            return convert(questionRepository.findByFaculty_IdEqualsAndAnswerNullAndSearchString(searchString, faculty.getId(), pageable));
        }

        return convert(questionRepository.findByFaculty_IdEqualsAndAnswerNotNullAndSearchString(searchString, faculty.getId(), pageable));
    }

    @Secured({"ROLE_DEAN", "ROLE_ADVISER"})
    @Override
    public void transferQuestion(QuestionTransferRequest request) {

        Question toBeSavedQuestion = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new UteForumException("Câu hỏi không tồn tại", HttpStatus.NOT_FOUND));

        toBeSavedQuestion.setTopic(topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new UteForumException("Chủ đề không tồn tại", HttpStatus.NOT_FOUND)));

        Optional<Long> optionalAdviserId = userRepository.findAdviserIdForAssigningQuestionByTopicId(toBeSavedQuestion.getTopic().getId());
        Long adviserId = optionalAdviserId.orElseGet(() -> userRepository.findAdviserIdForAssigningQuestionByFaculty(toBeSavedQuestion.getTopic().getFaculty().getId())
                .orElseThrow(() -> new UteForumException("Không tìm được tư vấn viên phù hợp", HttpStatus.NOT_FOUND)));

        toBeSavedQuestion.setAdviser(userRepository.findById(adviserId)
                .orElseThrow(() -> new UteForumException("Không tìm được tư vấn viên phù hợp", HttpStatus.NOT_FOUND)));

        Question savedQuestion = questionRepository.save(toBeSavedQuestion);
        log.info("Question saved successfully!");

        // send email message to new adviser
        executorService.execute(() -> {
            String message = "<p>" + "Chào " + savedQuestion.getAdviser().getFullName() + "</p>" +
                    "<p>" + "Với vai trò Tư vấn viên, bạn được giao 1 câu hỏi mới." + "</p>" +
                    "<p>" + MessageFormat.format("Mời bạn xem và trả lời tại <a href=\"{0}\">{0}</a>", feQuestionReplierPath + savedQuestion.getId()) + "</p>";
            emailService.sendHtmlMessage(savedQuestion.getAdviser().getEmail(), "Mời trả lời câu hỏi", message);

        });

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

    private String standardizeSearchString(String searchString) {
        String result;
        if (ObjectUtils.isEmpty(searchString)) result = "";
        else result = searchString.toUpperCase();

        return result;
    }
}
