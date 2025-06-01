package com.qbitspark.accountingplayground.service;

import com.qbitspark.accountingplayground.entity.Account;
import com.qbitspark.accountingplayground.entity.JournalEntry;
import com.qbitspark.accountingplayground.entity.JournalEntryLine;
import com.qbitspark.accountingplayground.payloads.AccountLedger;
import com.qbitspark.accountingplayground.payloads.GeneralLedgerReport;
import com.qbitspark.accountingplayground.payloads.LedgerEntry;
import com.qbitspark.accountingplayground.repo.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class GeneralLedgerService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public GeneralLedgerReport generateGeneralLedger() {
        List<Account> allAccounts = accountService.getAllAccounts();
        List<AccountLedger> accountLedgers = new ArrayList<>();

        for (Account account : allAccounts) {
            BigDecimal balance = accountService.getAccountBalance(account.getId());

            // Skip accounts with zero balance
            if (balance.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            AccountLedger ledger = generateAccountLedger(account);
            accountLedgers.add(ledger);
        }

        return new GeneralLedgerReport(accountLedgers);
    }

    private AccountLedger generateAccountLedger(Account account) {
        List<JournalEntry> allEntries = journalEntryRepository.findAll();
        List<LedgerEntry> ledgerEntries = new ArrayList<>();
        BigDecimal runningBalance = BigDecimal.ZERO;

        // Sort entries by date
        allEntries.sort(Comparator.comparing(JournalEntry::getTransactionDate));

        for (JournalEntry entry : allEntries) {
            for (JournalEntryLine line : entry.getLines()) {
                if (line.getAccountId().equals(account.getId())) {

                    BigDecimal debit = line.getDebitAmount() != null ? line.getDebitAmount() : BigDecimal.ZERO;
                    BigDecimal credit = line.getCreditAmount() != null ? line.getCreditAmount() : BigDecimal.ZERO;

                    // Calculate running balance based on account type
                    if (account.getType().name().equals("ASSET") || account.getType().name().equals("EXPENSE")) {
                        runningBalance = runningBalance.add(debit).subtract(credit);
                    } else {
                        runningBalance = runningBalance.add(credit).subtract(debit);
                    }

                    LedgerEntry ledgerEntry = new LedgerEntry(
                            entry.getTransactionDate(),
                            entry.getDescription(),
                            entry.getReferenceNumber(),
                            debit,
                            credit,
                            runningBalance
                    );

                    ledgerEntries.add(ledgerEntry);
                }
            }
        }

        return new AccountLedger(
                account.getAccountNumber(),
                account.getAccountName(),
                account.getType().name(),
                ledgerEntries,
                runningBalance
        );
    }
}