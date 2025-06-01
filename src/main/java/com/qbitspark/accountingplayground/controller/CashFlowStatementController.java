package com.qbitspark.accountingplayground.controller;

import com.qbitspark.accountingplayground.payloads.CashFlowStatementReport;
import com.qbitspark.accountingplayground.service.CashFlowStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class CashFlowStatementController {

    @Autowired
    private CashFlowStatementService cashFlowStatementService;

    @GetMapping("/cash-flow")
    public CashFlowStatementReport getCashFlowStatement() {
        return cashFlowStatementService.generateCashFlowStatement();
    }
}