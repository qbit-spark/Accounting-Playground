package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class TrialBalanceReport {
    private List<TrialBalanceItem> debitItems;
    private List<TrialBalanceItem> creditItems;
    private BigDecimal totalDebits;
    private BigDecimal totalCredits;

    public boolean isBalanced() {
        return totalDebits.compareTo(totalCredits) == 0;
    }
}