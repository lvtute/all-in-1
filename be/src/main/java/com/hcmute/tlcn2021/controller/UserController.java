package com.hcmute.tlcn2021.controller;

import com.hcmute.tlcn2021.payload.request.UserCreateByDeanRequest;
import com.hcmute.tlcn2021.payload.request.UserUpdateByDeanRequest;
import com.hcmute.tlcn2021.payload.request.UserUpdateRequest;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.payload.response.PaginationResponse;
import com.hcmute.tlcn2021.payload.response.SingleUserDetailsResponse;
import com.hcmute.tlcn2021.payload.response.UsersPaginationResponse;
import com.hcmute.tlcn2021.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<SingleUserDetailsResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping
    public ResponseEntity<MessageResponse> update(@RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(new MessageResponse(String.format("User %s updated successfully!", userService.update(userUpdateRequest).getUsername())));
    }

    @Secured({"ROLE_ADMIN", "ROLE_DEAN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("User with id '" + id
                + "' was successfully deleted"));
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<UsersPaginationResponse> findAll(@PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(userService.getPage(pageable));
    }

    @Secured({"ROLE_DEAN"})
    @GetMapping("/find-by-dean")
    public ResponseEntity<PaginationResponse> findByDean(@PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(userService.findByDean(pageable));
    }

    @Secured({"ROLE_DEAN"})
    @PutMapping("/update-by-dean")
    public ResponseEntity<MessageResponse> updateByDean(@RequestBody @Valid UserUpdateByDeanRequest userUpdateByDeanRequest) {
        userService.updateByDean(userUpdateByDeanRequest);
        return ResponseEntity.ok(new MessageResponse("Cập nhật người dùng thành công"));
    }

    @Secured({"ROLE_DEAN"})
    @PostMapping("/create-by-dean")
    public ResponseEntity<MessageResponse> createByDean(@RequestBody @Valid UserCreateByDeanRequest request) {
        userService.createByDean(request);
        return ResponseEntity.ok(new MessageResponse("Tạo người dùng thành công"));
    }
}
