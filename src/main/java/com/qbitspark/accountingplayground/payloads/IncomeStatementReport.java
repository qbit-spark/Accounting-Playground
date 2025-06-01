// IncomeStatementReport.java
package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class IncomeStatementReport {
    private List<IncomeStatementItem> revenueItems;
    private List<IncomeStatementItem> expenseItems;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpenses;
    private BigDecimal netProfit;

    public boolean isProfitable() {
        return netProfit.compareTo(BigDecimal.ZERO) > 0;
    }
}