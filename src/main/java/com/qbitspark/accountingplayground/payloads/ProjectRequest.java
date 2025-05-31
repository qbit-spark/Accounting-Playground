package com.qbitspark.accountingplayground.payloads;

import com.qbitspark.accountingplayground.enums.ProjectStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProjectRequest {
    private String name;
    private String description;
    private UUID companyId; // Should be a UUID in a real application
    private ProjectStatus status; // Should be an enum in a real application
    private BigDecimal budget; // Should be a BigDecimal in a real application
    private String createdBy; // Should be a UUID or similar identifier in a real application
}
