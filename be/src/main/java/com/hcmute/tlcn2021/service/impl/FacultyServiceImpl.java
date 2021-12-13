package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.exception.UteForumException;
import com.hcmute.tlcn2021.model.Faculty;
import com.hcmute.tlcn2021.payload.request.CreateFacultyRequest;
import com.hcmute.tlcn2021.payload.request.FacultyUpdateRequest;
import com.hcmute.tlcn2021.payload.response.FacultyResponse;
import com.hcmute.tlcn2021.payload.response.MessageResponse;
import com.hcmute.tlcn2021.repository.FacultyRepository;
import com.hcmute.tlcn2021.service.FacultyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return facultyRepository.findAllByIsDeletedFalse();
    }

    // hien thi theo id
    private FacultyResponse convertSingleFaculty(Faculty faculty) {
        return modelMapper
                .map(faculty, FacultyResponse.class);
    }

    @Override
    public FacultyResponse findById(Long id) {

        Faculty foundUser = facultyRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() ->
                new UteForumException("Không tìm thấy khoa", HttpStatus.NOT_FOUND));
        return convertSingleFaculty(foundUser);
    }

    // them moi
    @Override
    public MessageResponse createFaculty(CreateFacultyRequest createFacultyRequest) {
        Faculty faculty = new Faculty(createFacultyRequest.getName());

        faculty.setName(createFacultyRequest.getName());
        Faculty savedFaculty = facultyRepository.save(faculty);
        return new MessageResponse(String.format("Faculty %s create successfully!",
                savedFaculty.getName()));
    }

    @Override
    public FacultyResponse update(FacultyUpdateRequest facultyUpdateRequest) {
        Long id = facultyUpdateRequest.getId();

        Faculty foundFaculty = facultyRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() ->
                new UteForumException("Không tìm thấy khoa", HttpStatus.NOT_FOUND));

        foundFaculty.setName(facultyUpdateRequest.getName());
        facultyRepository.save(foundFaculty);
        return convertSingleFaculty(facultyRepository.save(foundFaculty));
    }

    // xoa voi id
    @Override
    @Transactional
    public void deleteById(Long id) {
        int affectedRows = facultyRepository.softDeleteFaculty(id);
        if (affectedRows == 0)
            throw new UteForumException("Có lỗi xảy ra khi xóa khoa", HttpStatus.NOT_FOUND);
    }
}
