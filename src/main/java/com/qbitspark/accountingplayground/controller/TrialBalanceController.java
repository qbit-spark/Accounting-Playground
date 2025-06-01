package com.qbitspark.accountingplayground.controller;


import com.qbitspark.accountingplayground.payloads.TrialBalanceReport;
import com.qbitspark.accountingplayground.service.TrialBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class TrialBalanceController {

    @Autowired
    private TrialBalanceService trialBalanceService;

    @GetMapping("/trial-balance")
    public TrialBalanceReport getTrialBalance() {
        return trialBalanceService.generateTrialBalance();
    }
}