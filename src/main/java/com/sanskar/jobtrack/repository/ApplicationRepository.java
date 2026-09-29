package com.sanskar.jobtrack.repository;

import com.sanskar.jobtrack.entity.Application;
import com.sanskar.jobtrack.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByStatus(ApplicationStatus status);

    List<Application> findByCompanyNameContainingIgnoreCase(String companyName);

    long countByStatus(ApplicationStatus status);
}