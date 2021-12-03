package com.hcmute.tlcn2021.controller;

import com.hcmute.tlcn2021.exception.UteForumException;
import com.hcmute.tlcn2021.model.User;
import com.hcmute.tlcn2021.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/adviser")
    @PreAuthorize("hasRole('ADVISER') or hasRole('DEAN') or hasRole('ADMIN')")
    public String adviserAccess() {
        return "Adviser Content.";
    }

    @GetMapping("/dean")
    @PreAuthorize("hasRole('DEAN')")
    public String deanAccess() {
        return "Dean Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/find-user-for-assigning-question-by-topic/{id}")
    public Long findUserForAssigningQuestion(@PathVariable Long id) {
        return userRepository.findAdviserIdForAssigningQuestionByTopicId(id).orElseThrow(() -> new UteForumException("no user available", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/find-user-for-assigning-question-by-faculty/{id}")
    public Long findUserForAssigningQuestionByFaculty(@PathVariable Long id) {
        return userRepository.findAdviserIdForAssigningQuestionByFaculty(id).orElseThrow(() -> new UteForumException("no user available", HttpStatus.NOT_FOUND));
    }

}