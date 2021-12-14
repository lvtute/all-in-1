package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.payload.response.FacultyUserPieChartDataResponse;

import java.util.List;

public interface ChartService {
    List<FacultyUserPieChartDataResponse> getFacultyUserPieChartData();
}
