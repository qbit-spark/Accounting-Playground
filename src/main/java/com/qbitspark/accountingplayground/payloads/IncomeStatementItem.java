// IncomeStatementItem.java
package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class IncomeStatementItem {
    private String accountNumber;
    private String accountName;
    private BigDecimal amount;
}