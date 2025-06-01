package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DashboardSummary {
    private BigDecimal cashPosition;
    private BigDecimal netProfit;
    private BigDecimal totalAssets;
    private BigDecimal profitMarginPercentage;

    public boolean isProfitable() {
        return netProfit.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean hasStrongCash() {
        return cashPosition.compareTo(BigDecimal.valueOf(10000)) > 0;
    }
}