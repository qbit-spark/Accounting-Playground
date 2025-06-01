package com.qbitspark.accountingplayground.service;

import com.qbitspark.accountingplayground.entity.Account;
import com.qbitspark.accountingplayground.enums.AccountType;
import com.qbitspark.accountingplayground.payloads.TrialBalanceItem;
import com.qbitspark.accountingplayground.payloads.TrialBalanceReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrialBalanceService {

    @Autowired
    private AccountService accountService;

    public TrialBalanceReport generateTrialBalance() {
        List<Account> allAccounts = accountService.getAllAccounts();

        List<TrialBalanceItem> debitItems = new ArrayList<>();
        List<TrialBalanceItem> creditItems = new ArrayList<>();

        BigDecimal totalDebits = BigDecimal.ZERO;
        BigDecimal totalCredits = BigDecimal.ZERO;

        for (Account account : allAccounts) {
            BigDecimal balance = accountService.getAccountBalance(account.getId());

            // Skip accounts with zero balance
            if (balance.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            TrialBalanceItem item = new TrialBalanceItem(
                    account.getAccountNumber(),
                    account.getAccountName(),
                    balance
            );

            // Assets & Expenses go on DEBIT side (left)
            if (account.getType() == AccountType.ASSET || account.getType() == AccountType.EXPENSE) {
                debitItems.add(item);
                totalDebits = totalDebits.add(balance);
            }
            // Liabilities, Equity & Revenue go on CREDIT side (right)
            else {
                creditItems.add(item);
                totalCredits = totalCredits.add(balance);
            }
        }

        return new TrialBalanceReport(debitItems, creditItems, totalDebits, totalCredits);
    }
}