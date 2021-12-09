package com.hcmute.tlcn2021.controller;

import com.hcmute.tlcn2021.model.Topic;
import com.hcmute.tlcn2021.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/find-by-faculty-id/{id}")
    public ResponseEntity<List<Topic>> findByFacultyId(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.findByFacultyId(id));
    }
}
