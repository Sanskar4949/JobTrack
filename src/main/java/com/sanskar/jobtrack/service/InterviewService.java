package com.sanskar.jobtrack.service;

import com.sanskar.jobtrack.dto.InterviewRequest;
import com.sanskar.jobtrack.dto.InterviewResponse;
import com.sanskar.jobtrack.entity.Application;
import com.sanskar.jobtrack.entity.Interview;
import com.sanskar.jobtrack.exception.ResourceNotFoundException;
import com.sanskar.jobtrack.repository.ApplicationRepository;
import com.sanskar.jobtrack.repository.InterviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;

    public InterviewService(
            InterviewRepository interviewRepository,
            ApplicationRepository applicationRepository) {

        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
    }

    public InterviewResponse createInterview(
            Long applicationId,
            InterviewRequest request) {

        Application application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Application not found with id: " + applicationId
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

        if (!applicationRepository.existsById(applicationId)) {
            throw new ResourceNotFoundException(
                    "Application not found with id: " + applicationId
            );
        }

        return interviewRepository
                .findByApplicationId(applicationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public InterviewResponse getInterviewById(Long id) {

        Interview interview = interviewRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Interview not found with id: " + id
                        ));

        return mapToResponse(interview);
    }

    public InterviewResponse updateInterview(
            Long id,
            InterviewRequest request) {

        Interview interview = interviewRepository
                .findById(id)
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

    public void deleteInterview(Long id) {

        if (!interviewRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Interview not found with id: " + id
            );
        }

        interviewRepository.deleteById(id);
    }

    private InterviewResponse mapToResponse(Interview interview) {

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