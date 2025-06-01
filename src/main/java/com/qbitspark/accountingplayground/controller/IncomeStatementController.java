package com.qbitspark.accountingplayground.controller;

import com.qbitspark.accountingplayground.payloads.IncomeStatementReport;
import com.qbitspark.accountingplayground.service.IncomeStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class IncomeStatementController {

    @Autowired
    private IncomeStatementService incomeStatementService;

    @GetMapping("/income-statement")
    public IncomeStatementReport getIncomeStatement() {
        return incomeStatementService.generateIncomeStatement();
    }
}