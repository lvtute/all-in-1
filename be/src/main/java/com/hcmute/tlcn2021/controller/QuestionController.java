package com.hcmute.tlcn2021.controller;

import com.hcmute.tlcn2021.payload.request.QuestionCreationRequest;
import com.hcmute.tlcn2021.payload.request.QuestionReplyRequest;
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
    public ResponseEntity<PaginationResponse> findAll(@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                                      @RequestParam(required = false) Long facultyId) {
        return ResponseEntity.ok(questionService.findAll(facultyId, pageable));
    }

    @Secured({"ROLE_DEAN", "ROLE_ADVISER"})
    @GetMapping("/find-by-adviser-id")
    public ResponseEntity<PaginationResponse> findAllByAdviserId(@PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                                                 @RequestParam(required = false, defaultValue = "false") Boolean noAnswerOnly) {
        return ResponseEntity.ok(questionService.findAllByAdviserId(noAnswerOnly, pageable));
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
}
