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
                .orElseThrow(() -> new UteForumException("Khoa kh??ng t???n t???i.", HttpStatus.NOT_FOUND)));
        toBeSavedQuestion.setTopic(topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new UteForumException("Ch??? ????? kh??ng t???n t???i", HttpStatus.NOT_FOUND)));
        if (!toBeSavedQuestion.getTopic().getFaculty().getId().equals(request.getFacultyId())) {
            throw new UteForumException("Ch??? ????? " + toBeSavedQuestion.getTopic().getName() + " kh??ng thu???c khoa " + toBeSavedQuestion.getFaculty().getName(),
                    HttpStatus.BAD_REQUEST);
        }

        Optional<Long> optionalAdviserId = userRepository.findAdviserIdForAssigningQuestionByTopicId(toBeSavedQuestion.getTopic().getId());
        Long adviserId = optionalAdviserId.orElseGet(() -> userRepository.findAdviserIdForAssigningQuestionByFaculty(toBeSavedQuestion.getFaculty().getId())
                .orElseThrow(() -> new UteForumException("Kh??ng t??m ???????c t?? v???n vi??n ph?? h???p", HttpStatus.NOT_FOUND)));

        toBeSavedQuestion.setAdviser(userRepository.findById(adviserId)
                .orElseThrow(() -> new UteForumException("Kh??ng t??m ???????c t?? v???n vi??n ph?? h???p", HttpStatus.NOT_FOUND)));

        Question savedQuestion = questionRepository.save(toBeSavedQuestion);
        log.info("Question saved successfully!");

        // send email to notify assigned adviser
        Runnable sendEmail = () -> {
            String message = "<p>" + "Ch??o " + savedQuestion.getAdviser().getFullName() + "</p>" +
                    "<p>" + "V???i vai tr?? T?? v???n vi??n, b???n ???????c giao 1 c??u h???i m???i." + "</p>" +
                    "<p>" + MessageFormat.format("M???i b???n xem v?? tr??? l???i t???i <a href=\"{0}\">{0}</a>", feQuestionReplierPath + savedQuestion.getId()) + "</p>";
            emailService.sendHtmlMessage(savedQuestion.getAdviser().getEmail(), "M???i tr??? l???i c??u h???i", message);

        };
        executorService.execute(sendEmail);

        return savedQuestion.getId();

    }

    @Secured({"ROLE_DEAN", "ROLE_ADVISER"})
    @Override
    public QuestionResponse findByIdIncludingPrivate(Long id) {

        Question foundQuestion = questionRepository.findById(id).orElseThrow(() -> new UteForumException("Kh??ng t??m th???y c??u h???i", HttpStatus.NOT_FOUND));

        increaseQuestionViews(foundQuestion);
        return convert(foundQuestion);
    }


    @Override
    public QuestionResponse findById(Long id) {

        Question foundQuestion = questionRepository.findByIdAndIsPrivateFalse(id).orElseThrow(() -> new UteForumException("Kh??ng t??m th???y c??u h???i", HttpStatus.NOT_FOUND));

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
                .orElseThrow(() -> new UteForumException("C??u h???i kh??ng t???n t??i", HttpStatus.NOT_FOUND));

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();

        if (question.getAdviser() != null && userDetails.getAuthorities().stream().noneMatch(r -> r.getAuthority().equals(ERole.ROLE_DEAN.toString()))
                && !userDetails.getId().equals(question.getAdviser().getId())) {
            throw new UteForumException("B???n kh??ng ph???i l?? t?? v???n vi??n ???????c giao c???a c??u h???i n??y", HttpStatus.FORBIDDEN);
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
            message.append("<p>").append("Ch??o ").append(question.getName()).append("</p>");
            message.append("<p>").append("C??u h???i c???a b???n v???i ti??u ?????: ").append(question.getTitle()).append(" ???? ???????c tr??? l???i b???i ").append(result.getAdviserFullName()).append("</p>");
            message.append("<p>").append(result.getAnswer()).append("</p>");
            if (!saved.isPrivate()) {
                message.append("<p>").append(MessageFormat.format("M???i b???n xem t???i ?????a ch??? <a href=\"{0}\">{0}</a>", feQuestionHomeDetailPath + question.getId())).append("</p>");
            } else {
                message.append("<p>").append("T?? v???n vi??n ???? ????nh d???u c??u h???i n??y l?? ri??ng t??. V?? v???y b???n ch??? m??nh b???n m???i c?? th??? xem c??u tr??? l???i t???i ????y, v?? c??u h???i s??? kh??ng xu???t hi???n tr??n web t?? v???n.").append("</p>");
            }
            emailService.sendHtmlMessage(question.getEmail(), "C??u h???i c???a b???n ???? ???????c tr??? l???i", message.toString());

            // send email to dean for approval
            if (questionReplyRequest.isConsultDean()) {

                User dean = userRepository.findByRole_NameEqualsAndFaculty_IdEqualsAndIsDeletedFalse(ERole.ROLE_DEAN, question.getFaculty().getId())
                        .orElseThrow(() -> new UteForumException("Kh??ng t??m ???????c Tr?????ng ban t?? v???n", HttpStatus.INTERNAL_SERVER_ERROR));
                if (ObjectUtils.isEmpty(dean.getEmail())) {
                    throw new UteForumException("Tr?????ng ban t?? v???n ch??a cung c???p email", HttpStatus.NOT_FOUND);
                }

                message = new StringBuilder();
                message.append("<p>").append("Ch??o ").append(dean.getFullName()).append("</p>");
                message.append("<p>").append("V???i vai tr?? Tr?????ng ban t?? v???n, b???n nh???n ???????c m???t ????? ngh??? ph?? duy???t c??u tr??? l???i").append("</p>");
                message.append("<p>").append(MessageFormat.format("M???i b???n xem t???i ?????a ch??? <a href=\"{0}\">{0}</a>", feQuestionReplierPath + question.getId())).append("</p>");
                emailService.sendHtmlMessage(dean.getEmail(), "Th?? xin ph?? duy???t c??u tr??? l???i", message.toString());
            }
        });


        return result;
    }

    @Secured({"ROLE_DEAN", "ROLE_ADVISER"})
    @Override
    @Transactional
    public void delete(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new UteForumException("C??u h???i kh??ng t???n t??i", HttpStatus.NOT_FOUND));

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();
        if (question.getAdviser() != null && userDetails.getAuthorities().stream().noneMatch(r -> r.getAuthority().equals(ERole.ROLE_DEAN.toString()))
                && !userDetails.getId().equals(question.getAdviser().getId())) {
            // any body can delete no-adviser questions, deans can delete any question

            throw new UteForumException("B???n kh??ng ph???i l?? t?? v???n vi??n ???????c giao c???a c??u h???i n??y", HttpStatus.FORBIDDEN);
        }

        questionRepository.deleteById(questionId);

        // send email notification about question is deleted
       executorService.execute(() -> {
            String message = "C??u h???i c???a b???n v???i ti??u ?????:\n" + question.getTitle() + "\n ???? b??? x??a.";
            emailService.sendSimpleMessage(question.getEmail(), "C??u h???i ???? b??? x??a", message);
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
                .orElseThrow(() -> new UteForumException("C??u h???i kh??ng t???n t???i", HttpStatus.NOT_FOUND));

        toBeSavedQuestion.setTopic(topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new UteForumException("Ch??? ????? kh??ng t???n t???i", HttpStatus.NOT_FOUND)));

        Optional<Long> optionalAdviserId = userRepository.findAdviserIdForAssigningQuestionByTopicId(toBeSavedQuestion.getTopic().getId());
        Long adviserId = optionalAdviserId.orElseGet(() -> userRepository.findAdviserIdForAssigningQuestionByFaculty(toBeSavedQuestion.getTopic().getFaculty().getId())
                .orElseThrow(() -> new UteForumException("Kh??ng t??m ???????c t?? v???n vi??n ph?? h???p", HttpStatus.NOT_FOUND)));

        toBeSavedQuestion.setAdviser(userRepository.findById(adviserId)
                .orElseThrow(() -> new UteForumException("Kh??ng t??m ???????c t?? v???n vi??n ph?? h???p", HttpStatus.NOT_FOUND)));

        Question savedQuestion = questionRepository.save(toBeSavedQuestion);
        log.info("Question saved successfully!");

        // send email message to new adviser
        executorService.execute(() -> {
            String message = "<p>" + "Ch??o " + savedQuestion.getAdviser().getFullName() + "</p>" +
                    "<p>" + "V???i vai tr?? T?? v???n vi??n, b???n ???????c giao 1 c??u h???i m???i." + "</p>" +
                    "<p>" + MessageFormat.format("M???i b???n xem v?? tr??? l???i t???i <a href=\"{0}\">{0}</a>", feQuestionReplierPath + savedQuestion.getId()) + "</p>";
            emailService.sendHtmlMessage(savedQuestion.getAdviser().getEmail(), "M???i tr??? l???i c??u h???i", message);

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
