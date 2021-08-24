package com.hcmute.tlcn2021.controller;

import com.hcmute.tlcn2021.payload.request.UserUpdateRequest;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.payload.response.UserDetailsResponse;
import com.hcmute.tlcn2021.payload.response.UsersPaginationResponse;
import com.hcmute.tlcn2021.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping
    public ResponseEntity<UserDetailsResponse> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(userService.update(userUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("User with id '" + id
                + "' was successfully deleted"));
    }

    @GetMapping
    public ResponseEntity<UsersPaginationResponse> getAll(@PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(userService.getPage(pageable));
    }
}
