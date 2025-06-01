package com.qbitspark.accountingplayground.payloads;

// TrialBalanceItem.java
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TrialBalanceItem {
    private String accountNumber;
    private String accountName;
    private BigDecimal balance;
}