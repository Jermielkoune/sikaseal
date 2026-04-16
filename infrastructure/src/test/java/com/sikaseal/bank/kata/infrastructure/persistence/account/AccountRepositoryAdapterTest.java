package com.sikaseal.bank.kata.infrastructure.persistence.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sikaseal.bank.kata.domain.model.Account;
import com.sikaseal.bank.kata.domain.model.AccountType;
import com.sikaseal.bank.kata.domain.model.CurrentAccount;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryAdapterTest {

  @Mock private SpringDataAccountRepository springDataAccountRepository;

  @Test
  void should_map_entity_to_domain_when_finding_by_id() {
    AccountRepositoryAdapter adapter = new AccountRepositoryAdapter(springDataAccountRepository);

    AccountEntity entity =
        new AccountEntity(
            "ACC-1", new BigDecimal("100.00"), AccountType.CURRENT, new BigDecimal("10.00"));

    when(springDataAccountRepository.findById("ACC-1")).thenReturn(Optional.of(entity));

    Optional<Account> result = adapter.findById("ACC-1");

    assertThat(result).isPresent();
    assertThat(result.get()).isInstanceOf(CurrentAccount.class);
    verify(springDataAccountRepository).findById("ACC-1");
  }

  @Test
  void should_return_empty_when_entity_is_not_found() {
    AccountRepositoryAdapter adapter = new AccountRepositoryAdapter(springDataAccountRepository);

    when(springDataAccountRepository.findById("MISSING")).thenReturn(Optional.empty());

    Optional<Account> result = adapter.findById("MISSING");

    assertThat(result).isEmpty();
  }

  @Test
  void should_save_domain_by_mapping_to_entity_and_back_when_saving() {
    AccountRepositoryAdapter adapter = new AccountRepositoryAdapter(springDataAccountRepository);

    Account domain = CurrentAccount.of("ACC-2", new BigDecimal("200.00"), new BigDecimal("50.00"));

    when(springDataAccountRepository.save(any(AccountEntity.class)))
        .thenAnswer(invocation -> invocation.getArgument(0, AccountEntity.class));

    Account saved = adapter.save(domain);

    assertThat(saved.getAccountId()).isEqualTo("ACC-2");
    assertThat(saved.getBalance()).isEqualByComparingTo(new BigDecimal("200.00"));

    verify(springDataAccountRepository).save(any(AccountEntity.class));
  }
}
