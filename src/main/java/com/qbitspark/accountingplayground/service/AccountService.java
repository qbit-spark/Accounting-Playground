package com.qbitspark.accountingplayground.service;

import com.qbitspark.accountingplayground.entity.Account;
import com.qbitspark.accountingplayground.entity.JournalEntryLine;
import com.qbitspark.accountingplayground.enums.AccountType;
import com.qbitspark.accountingplayground.repo.AccountRepository;
import com.qbitspark.accountingplayground.repo.JournalEntryLineRepository;
import com.qbitspark.accountingplayground.repo.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private JournalEntryLineRepository journalEntryLineRepository;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        account.setIsActive(true);
        return accountRepository.save(account);
    }

    public BigDecimal getAccountBalance(UUID accountId) {
        // Get all transaction lines for this account
        List<JournalEntryLine> lines = journalEntryLineRepository.findByAccountId(accountId);

        // Get the account to know its type
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) return BigDecimal.ZERO;

        // Calculate total debits and credits
        BigDecimal totalDebits = lines.stream()
                .map(line -> line.getDebitAmount() != null ? line.getDebitAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCredits = lines.stream()
                .map(line -> line.getCreditAmount() != null ? line.getCreditAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Apply the correct formula based on account type
        if (account.getType() == AccountType.ASSET || account.getType() == AccountType.EXPENSE) {
            return totalDebits.subtract(totalCredits);  // Debits - Credits
        } else {
            return totalCredits.subtract(totalDebits);  // Credits - Debits
        }
    }

    public List<Account> createDefaultAccounts() {
        List<Account> accounts = new ArrayList<>();

        // ASSETS
        accounts.add(createAccount("1000", "Cash", AccountType.ASSET));
        accounts.add(createAccount("1100", "Accounts Receivable", AccountType.ASSET));
        accounts.add(createAccount("1200", "Equipment", AccountType.ASSET));

        // LIABILITIES
        accounts.add(createAccount("2000", "Accounts Payable", AccountType.LIABILITY));
        accounts.add(createAccount("2100", "Equipment Loans", AccountType.LIABILITY));

        // EQUITY
        accounts.add(createAccount("3000", "Owner's Equity", AccountType.EQUITY));

        // REVENUE
        accounts.add(createAccount("4000", "Construction Revenue", AccountType.REVENUE));

        // EXPENSES
        accounts.add(createAccount("5000", "Materials", AccountType.EXPENSE));
        accounts.add(createAccount("5100", "Labor", AccountType.EXPENSE));
        accounts.add(createAccount("5200", "Equipment Rental", AccountType.EXPENSE));

        return accountRepository.saveAll(accounts);
    }

    private Account createAccount(String number, String name, AccountType type) {
        Account account = new Account();
        account.setAccountNumber(number);
        account.setAccountName(name);
        account.setType(type);
        account.setIsActive(true);
        return account;
    }
}