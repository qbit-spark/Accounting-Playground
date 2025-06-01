package com.qbitspark.accountingplayground.service;

import com.qbitspark.accountingplayground.entity.Account;
import com.qbitspark.accountingplayground.entity.JournalEntry;
import com.qbitspark.accountingplayground.entity.JournalEntryLine;
import com.qbitspark.accountingplayground.enums.AccountType;
import com.qbitspark.accountingplayground.payloads.CashFlowItem;
import com.qbitspark.accountingplayground.payloads.CashFlowStatementReport;
import com.qbitspark.accountingplayground.repo.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CashFlowStatementService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private AccountService accountService;

    public CashFlowStatementReport generateCashFlowStatement() {
        List<JournalEntry> allEntries = journalEntryRepository.findAll();

        List<CashFlowItem> operatingActivities = new ArrayList<>();
        List<CashFlowItem> investingActivities = new ArrayList<>();
        List<CashFlowItem> financingActivities = new ArrayList<>();

        BigDecimal operatingCash = BigDecimal.ZERO;
        BigDecimal investingCash = BigDecimal.ZERO;
        BigDecimal financingCash = BigDecimal.ZERO;

        // Find cash account dynamically
        Account cashAccount = findCashAccount();
        if (cashAccount == null) {
            throw new RuntimeException("Cash account not found!");
        }

        // Analyze each transaction for cash impact
        for (JournalEntry entry : allEntries) {
            CashFlowItem item = analyzeCashTransaction(entry, cashAccount);
            if (item != null) {
                switch (item.getCategory()) {
                    case "OPERATING":
                        operatingActivities.add(item);
                        operatingCash = operatingCash.add(item.getAmount());
                        break;
                    case "INVESTING":
                        investingActivities.add(item);
                        investingCash = investingCash.add(item.getAmount());
                        break;
                    case "FINANCING":
                        financingActivities.add(item);
                        financingCash = financingCash.add(item.getAmount());
                        break;
                }
            }
        }

        BigDecimal netCashChange = operatingCash.add(investingCash).add(financingCash);
        BigDecimal endingCash = accountService.getAccountBalance(cashAccount.getId());

        return new CashFlowStatementReport(
                operatingActivities, investingActivities, financingActivities,
                operatingCash, investingCash, financingCash,
                netCashChange, endingCash
        );
    }

    private Account findCashAccount() {
        // Look for account with number "1000" or name containing "Cash"
        return accountService.accountRepository.findByAccountNumber("1000")
                .orElse(accountService.getAllAccounts().stream()
                        .filter(account -> account.getAccountName().toLowerCase().contains("cash"))
                        .findFirst()
                        .orElse(null));
    }

    private CashFlowItem analyzeCashTransaction(JournalEntry entry, Account cashAccount) {
        boolean involvesCash = false;
        BigDecimal cashImpact = BigDecimal.ZERO;
        Account otherAccount = null;

        // Check if transaction involves cash and find the other account
        for (JournalEntryLine line : entry.getLines()) {
            if (line.getAccountId().equals(cashAccount.getId())) {
                involvesCash = true;
                if (line.getDebitAmount() != null && line.getDebitAmount().compareTo(BigDecimal.ZERO) > 0) {
                    cashImpact = line.getDebitAmount(); // Cash increase
                } else if (line.getCreditAmount() != null && line.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
                    cashImpact = line.getCreditAmount().negate(); // Cash decrease
                }
            } else {
                // Find the other account involved
                otherAccount = accountService.accountRepository.findById(line.getAccountId()).orElse(null);
            }
        }

        if (!involvesCash || otherAccount == null) {
            return null;
        }

        // Categorize based on the other account's type and characteristics
        String category = categorizeByAccountType(otherAccount);
        return new CashFlowItem(entry.getDescription(), cashImpact, category);
    }

    private String categorizeByAccountType(Account account) {
        switch (account.getType()) {
            case ASSET:
                if (isLongTermAsset(account)) {
                    return "INVESTING";    // Equipment, Buildings, Vehicles
                }
                return "OPERATING";        // Inventory, Accounts Receivable

            case LIABILITY:
                if (isLongTermLiability(account)) {
                    return "FINANCING";    // Loans, Mortgages
                }
                return "OPERATING";        // Accounts Payable, Accrued Expenses

            case EQUITY:
                return "FINANCING";        // Owner investments, retained earnings

            case REVENUE:
            case EXPENSE:
                return "OPERATING";        // All business operations

            default:
                return "OPERATING";
        }
    }

    private boolean isLongTermAsset(Account account) {
        String name = account.getAccountName().toLowerCase();
        String number = account.getAccountNumber();

        // Check by name patterns
        return name.contains("equipment") || name.contains("building") ||
                name.contains("vehicle") || name.contains("machinery") ||
                name.contains("furniture") || name.contains("computer") ||
                // Check by account number (1200+ typically fixed assets)
                (number.startsWith("12") || number.startsWith("13") || number.startsWith("14"));
    }

    private boolean isLongTermLiability(Account account) {
        String name = account.getAccountName().toLowerCase();
        String number = account.getAccountNumber();

        // Check by name patterns
        return name.contains("loan") || name.contains("mortgage") ||
                name.contains("note payable") ||
                // Check by account number (2100+ typically long-term liabilities)
                number.startsWith("21");
    }
}