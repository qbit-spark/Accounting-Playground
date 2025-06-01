package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashFlowItem {
    private String description;
    private BigDecimal amount;
    private String category; // OPERATING, INVESTING, FINANCING

    // Constructor without category (defaults to empty)
    public CashFlowItem(String description, BigDecimal amount) {
        this.description = description;
        this.amount = amount;
        this.category = "";
    }
}