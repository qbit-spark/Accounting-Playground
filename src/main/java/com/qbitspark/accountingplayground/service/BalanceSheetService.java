package com.qbitspark.accountingplayground.service;

import com.qbitspark.accountingplayground.entity.Account;
import com.qbitspark.accountingplayground.enums.AccountType;
import com.qbitspark.accountingplayground.payloads.BalanceSheetItem;
import com.qbitspark.accountingplayground.payloads.BalanceSheetReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceSheetService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private IncomeStatementService incomeStatementService;

    public BalanceSheetReport generateBalanceSheet() {
        List<Account> allAccounts = accountService.getAllAccounts();

        List<BalanceSheetItem> assetItems = new ArrayList<>();
        List<BalanceSheetItem> liabilityItems = new ArrayList<>();
        List<BalanceSheetItem> equityItems = new ArrayList<>();

        BigDecimal totalAssets = BigDecimal.ZERO;
        BigDecimal totalLiabilities = BigDecimal.ZERO;
        BigDecimal totalEquity = BigDecimal.ZERO;

        for (Account account : allAccounts) {
            BigDecimal balance = accountService.getAccountBalance(account.getId());

            if (balance.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            BalanceSheetItem item = new BalanceSheetItem(
                    account.getAccountNumber(),
                    account.getAccountName(),
                    balance
            );

            if (account.getType() == AccountType.ASSET) {
                assetItems.add(item);
                totalAssets = totalAssets.add(balance);
            } else if (account.getType() == AccountType.LIABILITY) {
                liabilityItems.add(item);
                totalLiabilities = totalLiabilities.add(balance);
            } else if (account.getType() == AccountType.EQUITY) {
                equityItems.add(item);
                totalEquity = totalEquity.add(balance);
            }
            // Skip Revenue and Expense accounts - they're summarized in Retained Earnings
        }

        // Add Retained Earnings (Net Profit) to equity
        BigDecimal netProfit = incomeStatementService.generateIncomeStatement().getNetProfit();
        if (netProfit.compareTo(BigDecimal.ZERO) != 0) {
            BalanceSheetItem retainedEarnings = new BalanceSheetItem(
                    "3100", "Retained Earnings", netProfit
            );
            equityItems.add(retainedEarnings);
            totalEquity = totalEquity.add(netProfit);
        }

        return new BalanceSheetReport(assetItems, liabilityItems, equityItems,
                totalAssets, totalLiabilities, totalEquity);
    }
}