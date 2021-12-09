package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.exception.*;
import com.hcmute.tlcn2021.model.Faculty;
import com.hcmute.tlcn2021.model.User;
import com.hcmute.tlcn2021.payload.request.CreateFacultyRequest;
import com.hcmute.tlcn2021.payload.request.FacultyUpdateRequest;
import com.hcmute.tlcn2021.payload.request.SignupRequest;
import com.hcmute.tlcn2021.payload.response.FacultyResponse;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.repository.FacultyRepository;
import com.hcmute.tlcn2021.service.FacultyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }

    // hien thi theo id
    private FacultyResponse convertSingleFaculty(Faculty faculty) {
        return modelMapper
                .map(faculty, FacultyResponse.class);
    }
    @Override
    public FacultyResponse findById(Long id) {

        Faculty foundUser = facultyRepository.findById(id).orElseThrow(() ->
                new FacultyNotFoundException("User with id = " + id + " can not be found!"));
        return convertSingleFaculty(foundUser);
    }

    // them moi
    @Override
    public MessageResponse createFaculty(CreateFacultyRequest createFacultyRequest) {
        Faculty faculty = new Faculty(createFacultyRequest.getName());

//        user.setRole(roleRepository.findById(createFacultyRequest.getRoleId())
//                .orElseThrow(() -> new CustomedRoleNotFoundException("Role with id = " + createFacultyRequest.getRoleId() + "does not exist")));
//
//        if (createFacultyRequest.getFacultyId() != 0) {
//            user.setFaculty(facultyRepository.findById(createFacultyRequest.getFacultyId())
//                    .orElseThrow(() -> new FacultyNotFoundException(
//                            "Faculty with id = '" + createFacultyRequest.getFacultyId() +
//                                    "' does not exist"
//                    )));
//        } else {
//            user.setFaculty(null);
//        }
        faculty.setName(createFacultyRequest.getName());
        Faculty savedFaculty = facultyRepository.save(faculty);
        return new MessageResponse(String.format("Faculty %s create successfully!",
                savedFaculty.getName()));
    }
    // cap nhat


    @Override
    public FacultyResponse update(FacultyUpdateRequest facultyUpdateRequest) {
        Long id = facultyUpdateRequest.getId();

        Faculty foundUser = facultyRepository.findById(id).orElseThrow(() ->
                new FacultyNotFoundException("Faculty with id = " + id + " can not be found!"));

        foundUser.setName(facultyUpdateRequest.getName());
        facultyRepository.save(foundUser);
        return convertSingleFaculty(facultyRepository.save(foundUser));
    }

    // xoa voi id
    @Override
    @Transactional
    public void deleteById(Long id) {
        int affectedRows = facultyRepository.softDeleteFaculty(id);
        if (affectedRows == 0)
            throw new FacultyDeleteFailedException("There is error(s) trying to delete faculty with id = " + id);
    }
}
