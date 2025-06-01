package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CashFlowSummary {
    private BigDecimal operatingCash;
    private BigDecimal investingCash;
    private BigDecimal financingCash;
    private BigDecimal netCashChange;

    public boolean isOperatingCashPositive() {
        return operatingCash.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isOverallCashPositive() {
        return netCashChange.compareTo(BigDecimal.ZERO) > 0;
    }

    public String getCashFlowHealth() {
        if (operatingCash.compareTo(BigDecimal.ZERO) > 0) {
            return "Healthy - generating cash from operations";
        } else if (netCashChange.compareTo(BigDecimal.ZERO) > 0) {
            return "Moderate - positive overall but not from operations";
        } else {
            return "Concerning - burning cash";
        }
    }
}