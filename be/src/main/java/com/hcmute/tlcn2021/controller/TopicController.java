package com.hcmute.tlcn2021.controller;

import com.hcmute.tlcn2021.model.Topic;
import com.hcmute.tlcn2021.payload.request.CreateFacultyRequest;
import com.hcmute.tlcn2021.payload.request.CreateTopicRequest;
import com.hcmute.tlcn2021.payload.request.TopicUpdateRequest;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.payload.response.TopicResponse;
import com.hcmute.tlcn2021.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<List<Topic>> findAll() {

        return ResponseEntity.ok(topicService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.findById(id));
    }

//    @GetMapping("/find-by-faculty-id/{id}")
//    public ResponseEntity<List<Topic>> findByFacultyId(@PathVariable Long id) {
//        return ResponseEntity.ok(topicService.findByFacultyId(id));
//    }

    // them
    @PostMapping("/insert")
    public ResponseEntity<MessageResponse> createTopic(@RequestBody @Valid CreateTopicRequest createTopicRequest) {

        return ResponseEntity.ok(topicService.createTopic(createTopicRequest));
    }

    // cap nhat

    @PutMapping
    public ResponseEntity<MessageResponse> update(@RequestBody TopicUpdateRequest topicUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(new MessageResponse(String.format("Topic %s updated successfully!", topicService.update(topicUpdateRequest).getName())));
    }

    // xoa
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        topicService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Topic with id '" + id
                + "' was successfully deleted"));
    }

    @GetMapping("/find-by-faculty-id/{id}")
    public ResponseEntity<List<Topic>> findByFacultyId(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.findByFacultyId(id));
    }
}
