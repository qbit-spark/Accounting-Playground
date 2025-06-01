package com.qbitspark.accountingplayground.controller;

import com.qbitspark.accountingplayground.entity.JournalEntry;
import com.qbitspark.accountingplayground.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping
    public JournalEntry createTransaction(@RequestBody JournalEntry entry) {
        return journalEntryService.createTransaction(entry);
    }

    @GetMapping
    public List<JournalEntry> getAllTransactions() {
        return journalEntryService.getAllTransactions();
    }
}