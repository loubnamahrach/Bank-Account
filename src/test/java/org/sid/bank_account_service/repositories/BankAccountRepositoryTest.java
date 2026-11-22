package org.sid.bank_account_service.repositories;

import org.junit.jupiter.api.Test;
import org.sid.bank_account_service.entities.BankAccount;
import org.sid.bank_account_service.enums.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BankAccountRepositoryTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    void shouldSaveAndFindAccount() {
        String id = UUID.randomUUID().toString();
        bankAccountRepository.save(BankAccount.builder()
                .id(id).balance(5000.0).currency("MAD")
                .type(AccountType.SAVING_ACCOUNT).createdAt(new Date()).build());

        BankAccount found = bankAccountRepository.findById(id).orElseThrow();
        assertThat(found.getBalance()).isEqualTo(5000.0);
        assertThat(found.getType()).isEqualTo(AccountType.SAVING_ACCOUNT);
    }

    @Test
    void shouldFindByType() {
        bankAccountRepository.save(BankAccount.builder()
                .id(UUID.randomUUID().toString()).balance(100.0).currency("MAD")
                .type(AccountType.CURRENT_ACCOUNT).createdAt(new Date()).build());

        List<BankAccount> accounts = bankAccountRepository.findByType(AccountType.CURRENT_ACCOUNT);
        assertThat(accounts).isNotEmpty();
    }
}
