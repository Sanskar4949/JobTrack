package com.sanskar.jobtrack.repository;

import com.sanskar.jobtrack.entity.Application;
import com.sanskar.jobtrack.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    // Get applications belonging to a specific user
    List<Application> findByUserId(Long userId);

    // Pagination scoped to user
    Page<Application> findByUserId(
            Long userId,
            Pageable pageable
    );

    // Get one application only if it belongs to the user
    Optional<Application> findByIdAndUserId(
            Long id,
            Long userId
    );

    // Filter by status for a specific user
    List<Application> findByUserIdAndStatus(
            Long userId,
            ApplicationStatus status
    );

    // Search company for a specific user
    List<Application> findByUserIdAndCompanyNameContainingIgnoreCase(
            Long userId,
            String companyName
    );
    long countByUserId(Long userId);
    // Dashboard counts scoped to user
    long countByUserIdAndStatus(
            Long userId,
            ApplicationStatus status
    );
}