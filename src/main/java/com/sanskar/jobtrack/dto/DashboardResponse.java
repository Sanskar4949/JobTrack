package com.sanskar.jobtrack.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardResponse {

    private long totalApplications;
    private long applied;
    private long screening;
    private long interview;
    private long offers;
    private long rejected;
    private long withdrawn;
    private long totalInterviews;
}