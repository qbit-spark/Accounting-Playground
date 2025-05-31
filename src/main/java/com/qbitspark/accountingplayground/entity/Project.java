package com.qbitspark.accountingplayground.entity;

import com.qbitspark.accountingplayground.enums.ProjectStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String projectName;   // "Downtown Office Building"
    private String projectCode;   // "DOB-2025-001"
    private BigDecimal budget;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status; // ACTIVE, COMPLETED, ON_HOLD
    private UUID companyId;
}