package com.sanskar.jobtrack.dto;

import com.sanskar.jobtrack.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class ApplicationResponse {

    private Long id;
    private String companyName;
    private String jobTitle;
    private String location;
    private BigDecimal salary;
    private LocalDate applicationDate;
    private ApplicationStatus status;
    private String jobUrl;
    private String notes;
}