package com.sanskar.jobtrack.entity;

import com.sanskar.jobtrack.enums.InterviewResult;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "interviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roundName;

    private LocalDate interviewDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterviewResult result;

    @Column(length = 1000)
    private String feedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;
}