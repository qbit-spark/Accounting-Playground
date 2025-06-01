package com.qbitspark.accountingplayground.repo;

import com.qbitspark.accountingplayground.entity.JournalEntryLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JournalEntryLineRepository extends JpaRepository<JournalEntryLine, UUID> {
    List<JournalEntryLine> findByAccountId(UUID accountId);
}
