package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.model.Faculty;
import com.hcmute.tlcn2021.repository.FacultyRepository;
import com.hcmute.tlcn2021.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public List<Faculty> findAll() {
        return facultyRepository.findAll();
    }
}
