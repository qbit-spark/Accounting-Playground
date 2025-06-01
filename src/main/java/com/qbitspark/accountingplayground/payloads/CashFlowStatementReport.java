package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CashFlowStatementReport {
    private List<CashFlowItem> operatingActivities;
    private List<CashFlowItem> investingActivities;
    private List<CashFlowItem> financingActivities;

    private BigDecimal netCashFromOperating;
    private BigDecimal netCashFromInvesting;
    private BigDecimal netCashFromFinancing;

    private BigDecimal netCashChange;
    private BigDecimal endingCashBalance;

    public BigDecimal getBeginningCashBalance() {
        return endingCashBalance.subtract(netCashChange);
    }

    public boolean isCashPositive() {
        return endingCashBalance.compareTo(BigDecimal.ZERO) > 0;
    }
}