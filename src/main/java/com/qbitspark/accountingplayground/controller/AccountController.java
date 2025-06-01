package com.qbitspark.accountingplayground.controller;

import com.qbitspark.accountingplayground.entity.Account;
import com.qbitspark.accountingplayground.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping("/setup-defaults")
    public List<Account> setupDefaultAccounts() {
        return accountService.createDefaultAccounts();
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/{accountId}/balance")
    public BigDecimal getAccountBalance(@PathVariable UUID accountId) {
        return accountService.getAccountBalance(accountId);
    }
}