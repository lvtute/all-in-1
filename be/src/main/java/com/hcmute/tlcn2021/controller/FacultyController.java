package com.hcmute.tlcn2021.controller;

import com.hcmute.tlcn2021.model.Faculty;
import com.hcmute.tlcn2021.payload.request.CreateFacultyRequest;
import com.hcmute.tlcn2021.payload.request.FacultyUpdateRequest;
import com.hcmute.tlcn2021.payload.response.FacultyResponse;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping
    public ResponseEntity<List<Faculty>> findAll() {

        return ResponseEntity.ok(facultyService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<FacultyResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<MessageResponse> createFaculty(@RequestBody @Valid CreateFacultyRequest createFacultyRequest) {

        return ResponseEntity.ok(facultyService.createFaculty(createFacultyRequest));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping
    public ResponseEntity<MessageResponse> update(@RequestBody @Valid FacultyUpdateRequest facultyUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(new MessageResponse(String.format("Faculty %s updated successfully!", facultyService.update(facultyUpdateRequest).getName())));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
        facultyService.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Faculty with id '" + id
                + "' was successfully deleted"));
    }

}
