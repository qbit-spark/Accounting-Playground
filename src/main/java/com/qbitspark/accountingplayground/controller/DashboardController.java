package com.qbitspark.accountingplayground.controller;

import com.qbitspark.accountingplayground.payloads.FinancialDashboard;
import com.qbitspark.accountingplayground.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public FinancialDashboard getFinancialDashboard() {
        return dashboardService.generateDashboard();
    }
}