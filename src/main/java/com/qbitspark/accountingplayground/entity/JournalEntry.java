package com.qbitspark.accountingplayground.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDate transactionDate;
    private String description;    // "Bought cement for Downtown project"
    private String referenceNumber;
    private UUID projectId;        // Which project this relates to
    private UUID companyId;

    @OneToMany
    private List<JournalEntryLine> lines; // Debit and Credit lines
}
