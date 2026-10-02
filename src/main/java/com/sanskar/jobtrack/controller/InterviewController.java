package com.sanskar.jobtrack.controller;

import com.sanskar.jobtrack.dto.InterviewRequest;
import com.sanskar.jobtrack.dto.InterviewResponse;
import com.sanskar.jobtrack.service.InterviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications/{applicationId}/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<InterviewResponse> createInterview(
            @PathVariable Long applicationId,
            @Valid @RequestBody InterviewRequest request) {

        InterviewResponse created =
                interviewService.createInterview(
                        applicationId,
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @GetMapping
    public ResponseEntity<List<InterviewResponse>> getInterviews(
            @PathVariable Long applicationId) {

        return ResponseEntity.ok(
                interviewService.getInterviewsByApplication(
                        applicationId
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponse> getInterviewById(
            @PathVariable Long applicationId,
            @PathVariable Long id) {

        return ResponseEntity.ok(
                interviewService.getInterviewById(
                        applicationId,
                        id
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewResponse> updateInterview(
            @PathVariable Long applicationId,
            @PathVariable Long id,
            @Valid @RequestBody InterviewRequest request) {

        return ResponseEntity.ok(
                interviewService.updateInterview(
                        applicationId,
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(
            @PathVariable Long applicationId,
            @PathVariable Long id) {

        interviewService.deleteInterview(
                applicationId,
                id
        );

        return ResponseEntity.noContent().build();
    }
}