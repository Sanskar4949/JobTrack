package com.sanskar.jobtrack.controller;

import com.sanskar.jobtrack.dto.ApplicationRequest;
import com.sanskar.jobtrack.dto.ApplicationResponse;
import com.sanskar.jobtrack.enums.ApplicationStatus;
import com.sanskar.jobtrack.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(
            @Valid @RequestBody ApplicationRequest request) {

        ApplicationResponse created =
                applicationService.createApplication(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>>
    getAllApplications() {

        return ResponseEntity.ok(
                applicationService.getAllApplications()
        );
    }
    @GetMapping("/page")
    public ResponseEntity<Page<ApplicationResponse>> getApplicationsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
            
        return ResponseEntity.ok(
                applicationService.getApplicationsPaginated(page, size)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse>
    getApplicationById(@PathVariable Long id) {

        return ResponseEntity.ok(
                applicationService.getApplicationById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse>
    updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationRequest request) {

        return ResponseEntity.ok(
                applicationService.updateApplication(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @PathVariable Long id) {

        applicationService.deleteApplication(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ApplicationResponse>>
    getByStatus(@PathVariable ApplicationStatus status) {

        return ResponseEntity.ok(
                applicationService
                        .getApplicationsByStatus(status)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<ApplicationResponse>>
    searchByCompany(@RequestParam String company) {

        return ResponseEntity.ok(
                applicationService.searchByCompany(company)
        );
    }
}