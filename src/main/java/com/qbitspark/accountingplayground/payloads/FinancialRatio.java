package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class FinancialRatio {
    private String ratioName;
    private BigDecimal value;
    private String assessment;

    public boolean isHealthy() {
        // Define healthy thresholds
        switch (ratioName) {
            case "Current Ratio":
                return value.compareTo(BigDecimal.ONE) > 0;
            case "Debt-to-Equity":
                return value.compareTo(BigDecimal.valueOf(3)) < 0;
            case "Asset Turnover":
                return value.compareTo(BigDecimal.valueOf(0.5)) > 0;
            default:
                return true;
        }
    }
}