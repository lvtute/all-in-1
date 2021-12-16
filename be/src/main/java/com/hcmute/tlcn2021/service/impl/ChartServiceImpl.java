package com.hcmute.tlcn2021.service.impl;

import com.hcmute.tlcn2021.config.service.AuthenticationFacade;
import com.hcmute.tlcn2021.config.service.UserDetailsImpl;
import com.hcmute.tlcn2021.payload.response.PieChartDataResponse;
import com.hcmute.tlcn2021.repository.FacultyRepository;
import com.hcmute.tlcn2021.repository.TopicRepository;
import com.hcmute.tlcn2021.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Secured({"ROLE_ADMIN"})
    @Override
    public List<PieChartDataResponse> getFacultyUserPieChartData() {
        return facultyRepository.getFacultyUserPieChartDataResponse();
    }

    @Secured({"ROLE_DEAN"})
    @Override
    public List<PieChartDataResponse> getTopicUserPieChartData() {
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();
        return topicRepository.getTopicUserPieChartDataResponse(userDetails.getFaculty().getId());
    }

    @Secured({"ROLE_DEAN"})
    @Override
    public List<PieChartDataResponse> getTopicQuestionPieChartData() {
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationFacade.getAuthentication().getPrincipal();
        return topicRepository.getTopicQuestionPieChartDataResponse(userDetails.getFaculty().getId());
    }
}
