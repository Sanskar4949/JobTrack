package com.sanskar.jobtrack.repository;

import com.sanskar.jobtrack.entity.Interview;
import com.sanskar.jobtrack.enums.InterviewResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository
        extends JpaRepository<Interview, Long> {

    List<Interview> findByApplicationId(Long applicationId);

    List<Interview> findByResult(InterviewResult result);

    long countByApplication_User_Id(Long userId);

    Optional<Interview> findByIdAndApplicationIdAndApplication_User_Id(
            Long id,
            Long applicationId,
            Long userId
    );
}