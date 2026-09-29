package com.sanskar.jobtrack.dto;

import com.sanskar.jobtrack.enums.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ApplicationRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    private String location;

    @PositiveOrZero(message = "Salary cannot be negative")
    private BigDecimal salary;

    @NotNull(message = "Application date is required")
    private LocalDate applicationDate;

    @NotNull(message = "Application status is required")
    private ApplicationStatus status;

    private String jobUrl;

    private String notes;
}