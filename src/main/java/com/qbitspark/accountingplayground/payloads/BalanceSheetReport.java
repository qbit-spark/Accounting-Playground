package com.qbitspark.accountingplayground.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class BalanceSheetReport {
    private List<BalanceSheetItem> assetItems;
    private List<BalanceSheetItem> liabilityItems;
    private List<BalanceSheetItem> equityItems;
    private BigDecimal totalAssets;
    private BigDecimal totalLiabilities;
    private BigDecimal totalEquity;

    public boolean isBalanced() {
        BigDecimal leftSide = totalAssets;
        BigDecimal rightSide = totalLiabilities.add(totalEquity);
        return leftSide.compareTo(rightSide) == 0;
    }

    public BigDecimal getTotalLiabilitiesAndEquity() {
        return totalLiabilities.add(totalEquity);
    }
}