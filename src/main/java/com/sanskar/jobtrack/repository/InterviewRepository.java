package com.sanskar.jobtrack.repository;

import com.sanskar.jobtrack.entity.Interview;
import com.sanskar.jobtrack.enums.InterviewResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByApplicationId(Long applicationId);

    List<Interview> findByResult(InterviewResult result);
}