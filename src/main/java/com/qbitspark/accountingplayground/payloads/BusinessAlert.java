package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessAlert {
    private String severity; // SUCCESS, WARNING, ERROR
    private String title;
    private String message;

    public boolean isPositive() {
        return "SUCCESS".equals(severity);
    }

    public boolean requiresAttention() {
        return "WARNING".equals(severity) || "ERROR".equals(severity);
    }
}