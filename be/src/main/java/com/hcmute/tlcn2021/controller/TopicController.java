package com.hcmute.tlcn2021.controller;

import com.hcmute.tlcn2021.model.Topic;
import com.hcmute.tlcn2021.payload.request.CreateTopicRequest;
import com.hcmute.tlcn2021.payload.request.TopicUpdateRequest;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.payload.response.TopicResponse;
import com.hcmute.tlcn2021.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @GetMapping
    public ResponseEntity<List<Topic>> findAll() {

        return ResponseEntity.ok(topicService.findAll());
    }

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.findById(id));
    }

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @PostMapping
    public ResponseEntity<MessageResponse> createTopic(@RequestBody @Valid CreateTopicRequest createTopicRequest) {
        return ResponseEntity.ok(topicService.createTopic(createTopicRequest));
    }

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @PutMapping
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid TopicUpdateRequest topicUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(new MessageResponse(String.format("Chủ đề %s được cập nhật thành công!", topicService.update(topicUpdateRequest).getName())));
    }

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        topicService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Chủ đề với id = '" + id
                + "' đã được xóa thành công"));
    }

//    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @GetMapping("/find-by-faculty-id/{id}")
    public ResponseEntity<List<Topic>> findByFacultyId(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.findByFacultyId(id));
    }
}
