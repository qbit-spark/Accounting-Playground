package com.qbitspark.accountingplayground.controller;

import com.qbitspark.accountingplayground.payloads.GeneralLedgerReport;
import com.qbitspark.accountingplayground.service.GeneralLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class GeneralLedgerController {

    @Autowired
    private GeneralLedgerService generalLedgerService;

    @GetMapping("/general-ledger")
    public GeneralLedgerReport getGeneralLedger() {
        return generalLedgerService.generateGeneralLedger();
    }
}