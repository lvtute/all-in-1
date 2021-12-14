package com.hcmute.tlcn2021.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultyUserPieChartDataResponse {
    private String facultyName;
    private Long amountOfUsers;
}
