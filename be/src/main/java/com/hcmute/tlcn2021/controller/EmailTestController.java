package com.hcmute.tlcn2021.controller;


import com.hcmute.tlcn2021.payload.request.EmailTestRequest;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/email")
public class EmailTestController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<MessageResponse> sendEmail(@RequestBody EmailTestRequest emailTestRequest) {

        emailService.sendSimpleMessage(emailTestRequest.getReceiver(), emailTestRequest.getSubject(), emailTestRequest.getText());
        return ResponseEntity.ok(new MessageResponse("Email sent successfully!"));
    }
}
