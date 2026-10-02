package com.sanskar.jobtrack.service;

import com.sanskar.jobtrack.dto.DashboardResponse;
import com.sanskar.jobtrack.enums.ApplicationStatus;
import com.sanskar.jobtrack.repository.ApplicationRepository;
import com.sanskar.jobtrack.repository.InterviewRepository;
import com.sanskar.jobtrack.security.CurrentUserService;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;
    private final CurrentUserService currentUserService;

    public DashboardService(
            ApplicationRepository applicationRepository,
            InterviewRepository interviewRepository,
            CurrentUserService currentUserService) {

        this.applicationRepository = applicationRepository;
        this.interviewRepository = interviewRepository;
        this.currentUserService = currentUserService;
    }

    public DashboardResponse getDashboard() {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        return DashboardResponse.builder()
                .totalApplications(
                        applicationRepository.countByUserId(userId)
                )
                .applied(
                        applicationRepository.countByUserIdAndStatus(
                                userId,
                                ApplicationStatus.APPLIED
                        )
                )
                .screening(
                        applicationRepository.countByUserIdAndStatus(
                                userId,
                                ApplicationStatus.SCREENING
                        )
                )
                .interview(
                        applicationRepository.countByUserIdAndStatus(
                                userId,
                                ApplicationStatus.INTERVIEW
                        )
                )
                .offers(
                        applicationRepository.countByUserIdAndStatus(
                                userId,
                                ApplicationStatus.OFFER
                        )
                )
                .rejected(
                        applicationRepository.countByUserIdAndStatus(
                                userId,
                                ApplicationStatus.REJECTED
                        )
                )
                .withdrawn(
                        applicationRepository.countByUserIdAndStatus(
                                userId,
                                ApplicationStatus.WITHDRAWN
                        )
                )
                .totalInterviews(
                        interviewRepository.countByApplication_User_Id(userId)
                )
                .build();
    }
}