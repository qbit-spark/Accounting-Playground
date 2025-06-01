package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class FinancialDashboard {
    private DashboardSummary summary;
    private List<FinancialRatio> financialRatios;
    private CashFlowSummary cashFlowSummary;
    private List<BusinessAlert> alerts;

    public int getTotalAlerts() {
        return alerts.size();
    }

    public long getWarningCount() {
        return alerts.stream().filter(alert -> "WARNING".equals(alert.getSeverity())).count();
    }

    public long getSuccessCount() {
        return alerts.stream().filter(alert -> "SUCCESS".equals(alert.getSeverity())).count();
    }

    public String getOverallHealthStatus() {
        long warnings = getWarningCount();
        long successes = getSuccessCount();

        if (warnings == 0) return "EXCELLENT";
        if (successes > warnings) return "GOOD";
        if (successes == warnings) return "FAIR";
        return "NEEDS_ATTENTION";
    }
}