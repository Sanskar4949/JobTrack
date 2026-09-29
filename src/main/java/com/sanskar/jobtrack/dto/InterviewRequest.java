package com.sanskar.jobtrack.dto;

import com.sanskar.jobtrack.enums.InterviewResult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InterviewRequest {

    @NotBlank(message = "Round name is required")
    private String roundName;

    private LocalDate interviewDate;

    @NotNull(message = "Interview result is required")
    private InterviewResult result;

    private String feedback;
}