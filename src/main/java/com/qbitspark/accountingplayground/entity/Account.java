package com.qbitspark.accountingplayground.entity;


import com.qbitspark.accountingplayground.enums.AccountType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String accountNumber; // "5000"
    private String accountName;   // "Materials"
    private AccountType type;     // ASSET, LIABILITY, EQUITY, REVENUE, EXPENSE
    private UUID companyId;
    private Boolean isActive;
}
