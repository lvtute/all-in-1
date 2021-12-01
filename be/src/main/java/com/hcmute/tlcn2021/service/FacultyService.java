package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.model.Faculty;
import com.hcmute.tlcn2021.payload.request.FacultyUpdateRequest;
import com.hcmute.tlcn2021.payload.request.UserUpdateRequest;
import com.hcmute.tlcn2021.payload.response.FacultyResponse;
import com.hcmute.tlcn2021.payload.response.SingleUserDetailsResponse;

import java.util.List;

public interface FacultyService {
    List<Faculty> findAll();
    FacultyResponse findById(Long id);
    FacultyResponse update(FacultyUpdateRequest facultyUpdateRequest);
    void deleteById(Long id);
}
