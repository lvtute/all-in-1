package com.hcmute.tlcn2021.service;

import com.hcmute.tlcn2021.payload.response.PieChartDataResponse;

import java.util.List;

public interface ChartService {
    List<PieChartDataResponse> getFacultyUserPieChartData();

    List<PieChartDataResponse> getTopicUserPieChartData();

    List<PieChartDataResponse> getTopicQuestionPieChartData();
}
