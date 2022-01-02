package com.hcmute.tlcn2021.controller;

import com.hcmute.tlcn2021.payload.request.QuestionCreationRequest;
import com.hcmute.tlcn2021.payload.request.QuestionReplyRequest;
import com.hcmute.tlcn2021.payload.request.QuestionTransferRequest;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.payload.response.PaginationResponse;
import com.hcmute.tlcn2021.payload.response.QuestionResponse;
import com.hcmute.tlcn2021.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<PaginationResponse> findAll(@PageableDefault(sort = {"createdDate", "lastModifiedDate"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                      @RequestParam(required = false) Long facultyId,
                                                      @RequestParam(required = false) String searchString) {
        return ResponseEntity.ok(questionService.findAll(facultyId, searchString, pageable));
    }

    @Secured({"ROLE_ADVISER"})
    @GetMapping("/find-by-adviser-id")
    public ResponseEntity<PaginationResponse> findAllByAdviserId(@PageableDefault(sort = {"createdDate", "lastModifiedDate"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                                 @RequestParam(required = false, defaultValue = "false") Boolean noAnswerOnly,
                                                                 @RequestParam(required = false, defaultValue = "") String searchString) {
        return ResponseEntity.ok(questionService.findAllByAdviserId(noAnswerOnly, searchString, pageable));
    }

    @Secured({"ROLE_DEAN", "ROLE_ADVISER"})
    @PutMapping("/save-answer")
    public ResponseEntity<QuestionResponse> reply(@RequestBody @Valid QuestionReplyRequest questionReplyRequest) {

        return ResponseEntity.ok(questionService.saveAnswer(questionReplyRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.findById(id));
    }

    @Secured({"ROLE_DEAN", "ROLE_ADVISER"})
    @GetMapping("/find-by-id-including-private/{id}")
    public ResponseEntity<QuestionResponse> findByIdIncludingPrivate(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.findByIdIncludingPrivate(id));
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid QuestionCreationRequest request) {
        return ResponseEntity.ok(questionService.create(request));
    }

    @Secured({"ROLE_DEAN", "ROLE_ADMIN", "ROLE_ADVISER"})
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Xóa câu hỏi thành công"));
    }

    @Secured({"ROLE_DEAN"})
    @GetMapping("/find-by-dean")
    public ResponseEntity<PaginationResponse> findByDean(@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                                         @RequestParam(required = false, defaultValue = "false") Boolean noAnswerOnly,
                                                         @RequestParam(required = false, defaultValue = "false") Boolean passedToDean,
                                                         @RequestParam(required = false, defaultValue = "") String searchString) {
        return ResponseEntity.ok(questionService.findAllByDean(searchString, noAnswerOnly, passedToDean, pageable));
    }

    @Secured({"ROLE_DEAN", "ROLE_ADVISER"})
    @PutMapping("/transfer")
    public ResponseEntity<?> transferQuestion(@RequestBody @Valid QuestionTransferRequest questionTransferRequest) {

        questionService.transferQuestion(questionTransferRequest);
        return ResponseEntity.ok().build();
    }
}
