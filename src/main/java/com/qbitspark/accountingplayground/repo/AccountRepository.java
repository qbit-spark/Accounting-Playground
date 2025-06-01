package com.qbitspark.accountingplayground.repo;

import com.qbitspark.accountingplayground.entity.Account;
import com.qbitspark.accountingplayground.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByType(AccountType type);
    List<Account> findByIsActiveTrue();
}