package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class AccountLedger {
    private String accountNumber;
    private String accountName;
    private String accountType;
    private List<LedgerEntry> entries;
    private BigDecimal finalBalance;

    public int getTransactionCount() {
        return entries.size();
    }
}