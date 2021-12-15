package com.hcmute.tlcn2021.controller;

import com.hcmute.tlcn2021.payload.response.PieChartDataResponse;
import com.hcmute.tlcn2021.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/chart")
@Secured({"ROLE_ADMIN", "ROLE_DEAN"})
public class ChartDataController {

    @Autowired
    private ChartService chartService;

    @GetMapping("/faculty-user-pie-chart")
    public ResponseEntity<List<PieChartDataResponse>> getFacultyUserPieChartData() {
        return ResponseEntity.ok(chartService.getFacultyUserPieChartData());
    }

    @GetMapping("/topic-user-pie-chart")
    public ResponseEntity<List<PieChartDataResponse>> getTopicUserPieChartData() {
        return ResponseEntity.ok(chartService.getTopicUserPieChartData());
    }

    @GetMapping("/topic-question-pie-chart")
    public ResponseEntity<List<PieChartDataResponse>> getTopicQuestionPieChartData() {
        return ResponseEntity.ok(chartService.getTopicQuestionPieChartData());
    }

}
