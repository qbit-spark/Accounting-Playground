package com.qbitspark.accountingplayground.service;

import com.qbitspark.accountingplayground.payloads.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private TrialBalanceService trialBalanceService;

    @Autowired
    private IncomeStatementService incomeStatementService;

    @Autowired
    private BalanceSheetService balanceSheetService;

    @Autowired
    private CashFlowStatementService cashFlowStatementService;

    public FinancialDashboard generateDashboard() {
        // Get all reports
        TrialBalanceReport trialBalance = trialBalanceService.generateTrialBalance();
        IncomeStatementReport incomeStatement = incomeStatementService.generateIncomeStatement();
        BalanceSheetReport balanceSheet = balanceSheetService.generateBalanceSheet();
        CashFlowStatementReport cashFlow = cashFlowStatementService.generateCashFlowStatement();

        // Calculate key metrics
        DashboardSummary summary = calculateSummary(incomeStatement, balanceSheet, cashFlow);
        List<FinancialRatio> ratios = calculateRatios(incomeStatement, balanceSheet);
        CashFlowSummary cashFlowSummary = summarizeCashFlow(cashFlow);
        List<BusinessAlert> alerts = generateAlerts(summary, ratios, balanceSheet);

        return new FinancialDashboard(summary, ratios, cashFlowSummary, alerts);
    }

    private DashboardSummary calculateSummary(IncomeStatementReport income,
                                              BalanceSheetReport balance,
                                              CashFlowStatementReport cashFlow) {
        BigDecimal cashPosition = cashFlow.getEndingCashBalance();
        BigDecimal netProfit = income.getNetProfit();
        BigDecimal totalAssets = balance.getTotalAssets();
        BigDecimal totalRevenue = income.getTotalRevenue();

        // Calculate profit margin
        BigDecimal profitMargin = BigDecimal.ZERO;
        if (totalRevenue.compareTo(BigDecimal.ZERO) > 0) {
            profitMargin = netProfit.divide(totalRevenue, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        return new DashboardSummary(cashPosition, netProfit, totalAssets, profitMargin);
    }

    private List<FinancialRatio> calculateRatios(IncomeStatementReport income,
                                                 BalanceSheetReport balance) {
        List<FinancialRatio> ratios = new ArrayList<>();

        // Current Ratio (Current Assets / Current Liabilities)
        // For simplicity, assume all assets are current, no current liabilities
        BigDecimal currentRatio = balance.getTotalAssets(); // Simplified
        ratios.add(new FinancialRatio("Current Ratio", currentRatio, "Excellent liquidity"));

        // Debt-to-Equity Ratio
        BigDecimal debtToEquity = BigDecimal.ZERO;
        if (balance.getTotalEquity().compareTo(BigDecimal.ZERO) > 0) {
            debtToEquity = balance.getTotalLiabilities()
                    .divide(balance.getTotalEquity(), 2, RoundingMode.HALF_UP);
        }
        String debtAssessment = debtToEquity.compareTo(BigDecimal.valueOf(2)) > 0 ?
                "High leverage" : "Moderate leverage";
        ratios.add(new FinancialRatio("Debt-to-Equity", debtToEquity, debtAssessment));

        // Asset Turnover (Revenue / Total Assets)
        BigDecimal assetTurnover = BigDecimal.ZERO;
        if (balance.getTotalAssets().compareTo(BigDecimal.ZERO) > 0) {
            assetTurnover = income.getTotalRevenue()
                    .divide(balance.getTotalAssets(), 3, RoundingMode.HALF_UP);
        }
        String turnoverAssessment = assetTurnover.compareTo(BigDecimal.valueOf(0.5)) > 0 ?
                "Good efficiency" : "Room for improvement";
        ratios.add(new FinancialRatio("Asset Turnover", assetTurnover, turnoverAssessment));

        return ratios;
    }

    private CashFlowSummary summarizeCashFlow(CashFlowStatementReport cashFlow) {
        return new CashFlowSummary(
                cashFlow.getNetCashFromOperating(),
                cashFlow.getNetCashFromInvesting(),
                cashFlow.getNetCashFromFinancing(),
                cashFlow.getNetCashChange()
        );
    }

    private List<BusinessAlert> generateAlerts(DashboardSummary summary,
                                               List<FinancialRatio> ratios,
                                               BalanceSheetReport balance) {
        List<BusinessAlert> alerts = new ArrayList<>();

        // Check profitability
        if (summary.getNetProfit().compareTo(BigDecimal.ZERO) > 0) {
            alerts.add(new BusinessAlert("SUCCESS", "Profitable operations",
                    "Business is generating profit"));
        } else {
            alerts.add(new BusinessAlert("WARNING", "Unprofitable operations",
                    "Business is losing money"));
        }

        // Check cash position
        if (summary.getCashPosition().compareTo(BigDecimal.valueOf(10000)) > 0) {
            alerts.add(new BusinessAlert("SUCCESS", "Strong cash position",
                    "Sufficient cash for operations"));
        } else {
            alerts.add(new BusinessAlert("WARNING", "Low cash position",
                    "Monitor cash flow closely"));
        }

        // Check debt levels
        BigDecimal debtToEquity = balance.getTotalLiabilities()
                .divide(balance.getTotalEquity(), 2, RoundingMode.HALF_UP);
        if (debtToEquity.compareTo(BigDecimal.valueOf(3)) > 0) {
            alerts.add(new BusinessAlert("WARNING", "High debt-to-equity ratio",
                    "Consider reducing debt or increasing equity"));
        }

        return alerts;
    }
}