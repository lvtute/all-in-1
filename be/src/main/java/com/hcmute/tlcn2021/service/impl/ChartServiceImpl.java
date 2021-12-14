package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.payload.response.FacultyUserPieChartDataResponse;
import com.hcmute.tlcn2021.repository.FacultyRepository;
import com.hcmute.tlcn2021.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Secured({"ROLE_ADMIN"})
@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public List<FacultyUserPieChartDataResponse> getFacultyUserPieChartData() {
        return facultyRepository.getFacultyUserPieChartDataResponse();
    }
}
