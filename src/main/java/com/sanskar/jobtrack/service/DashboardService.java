package com.sanskar.jobtrack.service;

import com.sanskar.jobtrack.dto.DashboardResponse;
import com.sanskar.jobtrack.enums.ApplicationStatus;
import com.sanskar.jobtrack.repository.ApplicationRepository;
import com.sanskar.jobtrack.repository.InterviewRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;

    public DashboardService(
            ApplicationRepository applicationRepository,
            InterviewRepository interviewRepository) {

        this.applicationRepository = applicationRepository;
        this.interviewRepository = interviewRepository;
    }

    public DashboardResponse getDashboard() {

        return DashboardResponse.builder()
                .totalApplications(applicationRepository.count())
                .applied(applicationRepository.countByStatus(
                        ApplicationStatus.APPLIED))
                .screening(applicationRepository.countByStatus(
                        ApplicationStatus.SCREENING))
                .interview(applicationRepository.countByStatus(
                        ApplicationStatus.INTERVIEW))
                .offers(applicationRepository.countByStatus(
                        ApplicationStatus.OFFER))
                .rejected(applicationRepository.countByStatus(
                        ApplicationStatus.REJECTED))
                .withdrawn(applicationRepository.countByStatus(
                        ApplicationStatus.WITHDRAWN))
                .totalInterviews(interviewRepository.count())
                .build();
    }
}