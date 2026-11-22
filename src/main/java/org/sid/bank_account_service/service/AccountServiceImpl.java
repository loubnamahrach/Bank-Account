package org.sid.bank_account_service.service;

import jakarta.transaction.Transactional;
import org.sid.bank_account_service.dto.BankAccountRequestDTO;
import org.sid.bank_account_service.dto.BankAccountResponseDTO;
import org.sid.bank_account_service.entities.BankAccount;
import org.sid.bank_account_service.mappers.AccountMapper;
import org.sid.bank_account_service.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final BankAccountRepository bankAccountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(BankAccountRepository bankAccountRepository, AccountMapper accountMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO dto) {
        BankAccount bankAccount = BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(new Date())
                .balance(dto.getBalance())
                .currency(dto.getCurrency())
                .type(dto.getType())
                .build();
        return accountMapper.fromBankAccount(bankAccountRepository.save(bankAccount));
    }

    @Override
    public BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO dto) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Account %s not found", id)));
        if (dto.getBalance() != null) account.setBalance(dto.getBalance());
        if (dto.getCurrency() != null) account.setCurrency(dto.getCurrency());
        if (dto.getType() != null) account.setType(dto.getType());
        return accountMapper.fromBankAccount(bankAccountRepository.save(account));
    }
}
