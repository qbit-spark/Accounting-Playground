package com.qbitspark.accountingplayground.repo;

import com.qbitspark.accountingplayground.entity.JournalEntry;
import com.qbitspark.accountingplayground.entity.JournalEntryLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, UUID> {
    List<JournalEntry> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);

}