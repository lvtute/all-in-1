package com.hcmute.tlcn2021.controller;

import com.hcmute.tlcn2021.model.Question;
import com.hcmute.tlcn2021.model.Role;
import com.hcmute.tlcn2021.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private QuestionRepository questionRepository;

//    @GetMapping
//    public ResponseEntity<Page<Question>> findAll(@PageableDefault(sort = "created_date", direction = Sort.Direction.DESC) Pageable pageable) {
//        return ResponseEntity.ok(questionRepository.test("%ThEo%", pageable));
//    }
}
