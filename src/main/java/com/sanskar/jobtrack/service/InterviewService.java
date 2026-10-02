package com.sanskar.jobtrack.service;

import com.sanskar.jobtrack.dto.InterviewRequest;
import com.sanskar.jobtrack.dto.InterviewResponse;
import com.sanskar.jobtrack.entity.Application;
import com.sanskar.jobtrack.entity.Interview;
import com.sanskar.jobtrack.entity.User;
import com.sanskar.jobtrack.exception.ResourceNotFoundException;
import com.sanskar.jobtrack.repository.ApplicationRepository;
import com.sanskar.jobtrack.repository.InterviewRepository;
import com.sanskar.jobtrack.security.CurrentUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;
    private final CurrentUserService currentUserService;

    public InterviewService(
            InterviewRepository interviewRepository,
            ApplicationRepository applicationRepository,
            CurrentUserService currentUserService) {

        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
        this.currentUserService = currentUserService;
    }

    public InterviewResponse createInterview(
            Long applicationId,
            InterviewRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        Application application = applicationRepository
                .findByIdAndUserId(
                        applicationId,
                        currentUser.getId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Application not found with id: "
                                        + applicationId
                        ));

        Interview interview = Interview.builder()
                .roundName(request.getRoundName())
                .interviewDate(request.getInterviewDate())
                .result(request.getResult())
                .feedback(request.getFeedback())
                .application(application)
                .build();

        Interview savedInterview =
                interviewRepository.save(interview);

        return mapToResponse(savedInterview);
    }

    public List<InterviewResponse> getInterviewsByApplication(
            Long applicationId) {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        // Verify that the application belongs to the current user
        applicationRepository
                .findByIdAndUserId(applicationId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Application not found with id: "
                                        + applicationId
                        ));

        return interviewRepository
                .findByApplicationId(applicationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public InterviewResponse getInterviewById(
            Long applicationId,
            Long id) {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        Interview interview = interviewRepository
                .findByIdAndApplicationIdAndApplication_User_Id(
                        id,
                        applicationId,
                        userId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Interview not found with id: " + id
                        ));

        return mapToResponse(interview);
    }

    public InterviewResponse updateInterview(
            Long applicationId,
            Long id,
            InterviewRequest request) {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        Interview interview = interviewRepository
                .findByIdAndApplicationIdAndApplication_User_Id(
                        id,
                        applicationId,
                        userId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Interview not found with id: " + id
                        ));

        interview.setRoundName(request.getRoundName());
        interview.setInterviewDate(request.getInterviewDate());
        interview.setResult(request.getResult());
        interview.setFeedback(request.getFeedback());

        Interview updatedInterview =
                interviewRepository.save(interview);

        return mapToResponse(updatedInterview);
    }

    public void deleteInterview(
            Long applicationId,
            Long id) {

        Long userId = currentUserService
                .getCurrentUser()
                .getId();

        Interview interview = interviewRepository
                .findByIdAndApplicationIdAndApplication_User_Id(
                        id,
                        applicationId,
                        userId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Interview not found with id: " + id
                        ));

        interviewRepository.delete(interview);
    }

    private InterviewResponse mapToResponse(
            Interview interview) {

        return InterviewResponse.builder()
                .id(interview.getId())
                .applicationId(
                        interview.getApplication().getId()
                )
                .companyName(
                        interview.getApplication().getCompanyName()
                )
                .jobTitle(
                        interview.getApplication().getJobTitle()
                )
                .roundName(interview.getRoundName())
                .interviewDate(interview.getInterviewDate())
                .result(interview.getResult())
                .feedback(interview.getFeedback())
                .build();
    }
}