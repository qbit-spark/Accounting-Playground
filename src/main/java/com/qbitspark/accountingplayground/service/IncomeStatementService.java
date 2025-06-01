package com.qbitspark.accountingplayground.service;

import com.qbitspark.accountingplayground.entity.Account;
import com.qbitspark.accountingplayground.enums.AccountType;
import com.qbitspark.accountingplayground.payloads.IncomeStatementItem;
import com.qbitspark.accountingplayground.payloads.IncomeStatementReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeStatementService {

    @Autowired
    private AccountService accountService;

    public IncomeStatementReport generateIncomeStatement() {
        List<Account> allAccounts = accountService.getAllAccounts();

        List<IncomeStatementItem> revenueItems = new ArrayList<>();
        List<IncomeStatementItem> expenseItems = new ArrayList<>();

        BigDecimal totalRevenue = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;

        for (Account account : allAccounts) {
            BigDecimal balance = accountService.getAccountBalance(account.getId());

            // Skip accounts with zero balance
            if (balance.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            IncomeStatementItem item = new IncomeStatementItem(
                    account.getAccountNumber(),
                    account.getAccountName(),
                    balance
            );

            if (account.getType() == AccountType.REVENUE) {
                revenueItems.add(item);
                totalRevenue = totalRevenue.add(balance);
            } else if (account.getType() == AccountType.EXPENSE) {
                expenseItems.add(item);
                totalExpenses = totalExpenses.add(balance);
            }
            // We ignore Assets, Liabilities, and Equity for Income Statement
        }

        BigDecimal netProfit = totalRevenue.subtract(totalExpenses);

        return new IncomeStatementReport(revenueItems, expenseItems,
                totalRevenue, totalExpenses, netProfit);
    }
}