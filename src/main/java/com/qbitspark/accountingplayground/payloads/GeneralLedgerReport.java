package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class GeneralLedgerReport {
    private List<AccountLedger> accountLedgers;

    public int getTotalAccounts() {
        return accountLedgers.size();
    }

    public int getTotalTransactions() {
        return accountLedgers.stream()
                .mapToInt(AccountLedger::getTransactionCount)
                .sum();
    }
}