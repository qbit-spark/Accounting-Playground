package com.qbitspark.accountingplayground.controller;

import com.qbitspark.accountingplayground.payloads.BalanceSheetReport;
import com.qbitspark.accountingplayground.service.BalanceSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class BalanceSheetController {

    @Autowired
    private BalanceSheetService balanceSheetService;

    @GetMapping("/balance-sheet")
    public BalanceSheetReport getBalanceSheet() {
        return balanceSheetService.generateBalanceSheet();
    }
}