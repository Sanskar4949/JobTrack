package com.sanskar.jobtrack.service;

import com.sanskar.jobtrack.dto.ApplicationRequest;
import com.sanskar.jobtrack.dto.ApplicationResponse;
import com.sanskar.jobtrack.entity.Application;
import com.sanskar.jobtrack.entity.User;
import com.sanskar.jobtrack.enums.ApplicationStatus;
import com.sanskar.jobtrack.exception.ResourceNotFoundException;
import com.sanskar.jobtrack.repository.ApplicationRepository;
import com.sanskar.jobtrack.security.CurrentUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CurrentUserService currentUserService;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            CurrentUserService currentUserService) {

        this.applicationRepository = applicationRepository;
        this.currentUserService = currentUserService;
    }

    public ApplicationResponse createApplication(
            ApplicationRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        Application application = new Application();

        application.setUser(currentUser);
        application.setCompanyName(request.getCompanyName());
        application.setJobTitle(request.getJobTitle());
        application.setLocation(request.getLocation());
        application.setSalary(request.getSalary());
        application.setApplicationDate(request.getApplicationDate());
        application.setStatus(request.getStatus());
        application.setJobUrl(request.getJobUrl());
        application.setNotes(request.getNotes());

        Application saved = applicationRepository.save(application);

        return mapToResponse(saved);
    }

    public List<ApplicationResponse> getAllApplications() {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        return applicationRepository
                .findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Page<ApplicationResponse> getApplicationsPaginated(
            int page,
            int size) {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        Pageable pageable = PageRequest.of(page, size);

        return applicationRepository
                .findByUserId(userId, pageable)
                .map(this::mapToResponse);
    }

    public ApplicationResponse getApplicationById(Long id) {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        Application application = applicationRepository
                .findByIdAndUserId(id, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Application not found with id: " + id
                        )
                );

        return mapToResponse(application);
    }

    public ApplicationResponse updateApplication(
            Long id,
            ApplicationRequest request) {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        Application existing = applicationRepository
                .findByIdAndUserId(id, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Application not found with id: " + id
                        )
                );

        validateStatusTransition(
                existing.getStatus(),
                request.getStatus()
        );

        existing.setCompanyName(request.getCompanyName());
        existing.setJobTitle(request.getJobTitle());
        existing.setLocation(request.getLocation());
        existing.setSalary(request.getSalary());
        existing.setApplicationDate(request.getApplicationDate());
        existing.setStatus(request.getStatus());
        existing.setJobUrl(request.getJobUrl());
        existing.setNotes(request.getNotes());

        Application updated =
                applicationRepository.save(existing);

        return mapToResponse(updated);
    }

    private void validateStatusTransition(
            ApplicationStatus currentStatus,
            ApplicationStatus newStatus) {

        if (currentStatus == newStatus) {
            return;
        }

        boolean validTransition = switch (currentStatus) {

            case APPLIED ->
                    newStatus == ApplicationStatus.SCREENING ||
                    newStatus == ApplicationStatus.REJECTED ||
                    newStatus == ApplicationStatus.WITHDRAWN;

            case SCREENING ->
                    newStatus == ApplicationStatus.INTERVIEW ||
                    newStatus == ApplicationStatus.REJECTED ||
                    newStatus == ApplicationStatus.WITHDRAWN;

            case INTERVIEW ->
                    newStatus == ApplicationStatus.OFFER ||
                    newStatus == ApplicationStatus.REJECTED ||
                    newStatus == ApplicationStatus.WITHDRAWN;

            case OFFER,
                 REJECTED,
                 WITHDRAWN ->
                    false;
        };

        if (!validTransition) {
            throw new IllegalStateException(
                    "Invalid status transition from "
                            + currentStatus
                            + " to "
                            + newStatus
            );
        }
    }

    public void deleteApplication(Long id) {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        Application existing = applicationRepository
                .findByIdAndUserId(id, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Application not found with id: " + id
                        )
                );

        applicationRepository.delete(existing);
    }

    public List<ApplicationResponse> getApplicationsByStatus(
            ApplicationStatus status) {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        return applicationRepository
                .findByUserIdAndStatus(userId, status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ApplicationResponse> searchByCompany(
            String companyName) {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        return applicationRepository
                .findByUserIdAndCompanyNameContainingIgnoreCase(
                        userId,
                        companyName
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ApplicationResponse mapToResponse(
            Application application) {

        return ApplicationResponse.builder()
                .id(application.getId())
                .companyName(application.getCompanyName())
                .jobTitle(application.getJobTitle())
                .location(application.getLocation())
                .salary(application.getSalary())
                .applicationDate(application.getApplicationDate())
                .status(application.getStatus())
                .jobUrl(application.getJobUrl())
                .notes(application.getNotes())
                .build();
    }
}