package com.qbitspark.accountingplayground.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class JournalEntryLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID accountId;        // Which account (Materials, Cash, etc.)
    private BigDecimal debitAmount;
    private BigDecimal creditAmount;
    private UUID journalEntryId;
}
