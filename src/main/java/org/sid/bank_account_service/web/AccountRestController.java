package org.sid.bank_account_service.web;

import org.sid.bank_account_service.entities.BankAccount;
import org.sid.bank_account_service.repositories.BankAccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {

    private final BankAccountRepository bankAccountRepository;

    public AccountRestController(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @GetMapping("/bankAccounts")
    public List<BankAccount> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccount getAccount(@PathVariable String id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                String.format("Account %s not found", id)
                        )
                );
    }

    @PostMapping("/bankAccounts/{id}")
    public BankAccount update(
            @PathVariable String id,
            @RequestBody BankAccount bankAccount) {

        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Account not found")
                );

        if (bankAccount.getBalance() != null) {
            account.setBalance(bankAccount.getBalance());
        }

        if (bankAccount.getCreatedAt() != null) {
            account.setCreatedAt(new java.util.Date());
        }

        if (bankAccount.getType() != null) {
            account.setType(bankAccount.getType());
        }

        if (bankAccount.getCurrency() != null) {
            account.setCurrency(bankAccount.getCurrency());
        }

        return bankAccountRepository.save(account);
    }

    @DeleteMapping("/bankAccounts/{id}")
    public void delete(@PathVariable String id) {
        bankAccountRepository.deleteById(id);
    }
}