package com.sanskar.jobtrack.dto;

import com.sanskar.jobtrack.enums.InterviewResult;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class InterviewResponse {

    private Long id;
    private Long applicationId;
    private String companyName;
    private String jobTitle;
    private String roundName;
    private LocalDate interviewDate;
    private InterviewResult result;
    private String feedback;
}