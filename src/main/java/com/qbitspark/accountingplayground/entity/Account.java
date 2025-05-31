package com.qbitspark.accountingplayground.entity;


import com.qbitspark.accountingplayground.enums.AccountType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String accountNumber; // "5000"
    private String accountName;   // "Materials"
    private AccountType type;     // ASSET, LIABILITY, EQUITY, REVENUE, EXPENSE
    private Long companyId;
    private Boolean isActive;
}
