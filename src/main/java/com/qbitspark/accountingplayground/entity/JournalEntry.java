package com.qbitspark.accountingplayground.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDate transactionDate;
    private String description;    // "Bought cement for Downtown project"
    private String referenceNumber;
    private Long projectId;        // Which project this relates to
    private Long companyId;

    @OneToMany
    private List<JournalEntryLine> lines; // Debit and Credit lines
}
