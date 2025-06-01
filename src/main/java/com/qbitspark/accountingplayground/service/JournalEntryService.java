package com.qbitspark.accountingplayground.service;

import com.qbitspark.accountingplayground.entity.JournalEntry;
import com.qbitspark.accountingplayground.entity.JournalEntryLine;
import com.qbitspark.accountingplayground.repo.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry createTransaction(JournalEntry entry) {
        validateTransaction(entry);
        return journalEntryRepository.save(entry);
    }

    private void validateTransaction(JournalEntry entry) {
        if (entry.getLines().size() < 2) {
            throw new RuntimeException("Transaction must have at least 2 lines");
        }

        if (!isBalanced(entry)) {
            throw new RuntimeException("Debits must equal Credits");
        }
    }

    private boolean isBalanced(JournalEntry entry) {
        BigDecimal totalDebits = BigDecimal.ZERO;
        BigDecimal totalCredits = BigDecimal.ZERO;

        for (JournalEntryLine line : entry.getLines()) {
            if (line.getDebitAmount() != null) {
                totalDebits = totalDebits.add(line.getDebitAmount());
            }
            if (line.getCreditAmount() != null) {
                totalCredits = totalCredits.add(line.getCreditAmount());
            }
        }

        return totalDebits.compareTo(totalCredits) == 0;
    }

    public List<JournalEntry> getAllTransactions() {
        return journalEntryRepository.findAll();
    }
}
