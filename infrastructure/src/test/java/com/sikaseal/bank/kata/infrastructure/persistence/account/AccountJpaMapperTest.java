package com.sikaseal.bank.kata.infrastructure.persistence.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sikaseal.bank.kata.domain.model.Account;
import com.sikaseal.bank.kata.domain.model.AccountType;
import com.sikaseal.bank.kata.domain.model.CurrentAccount;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class AccountJpaMapperTest {

  @Test
  void should_map_current_account_to_entity_with_overdraft_when_account_is_current_account() {
    Account account = CurrentAccount.of("ACC-1", new BigDecimal("100.00"), new BigDecimal("50.00"));

    AccountEntity entity = AccountJpaMapper.toEntity(account);

    assertThat(entity.getAccountId()).isEqualTo("ACC-1");
    assertThat(entity.getBalance()).isEqualByComparingTo(new BigDecimal("100.00"));
    assertThat(entity.getAccountType()).isEqualTo(AccountType.CURRENT);
    assertThat(entity.getOverdraftLimit()).isEqualByComparingTo(new BigDecimal("50.00"));
  }

  @Test
  void should_map_non_current_account_to_entity_with_zero_overdraft_when_account_is_not_current() {
    Account account =
        new Account("ACC-2", new BigDecimal("10.00"), AccountType.SAVINGS) {
          @Override
          public Account deposit(BigDecimal amount) {
            return this;
          }

          @Override
          public Account withdraw(BigDecimal amount) {
            return this;
          }
        };

    AccountEntity entity = AccountJpaMapper.toEntity(account);

    assertThat(entity.getAccountType()).isEqualTo(AccountType.SAVINGS);
    assertThat(entity.getOverdraftLimit()).isEqualByComparingTo(BigDecimal.ZERO);
  }

  @Test
  void should_map_entity_to_domain_current_account_when_entity_type_is_current() {
    AccountEntity entity =
        new AccountEntity(
            "ACC-3", new BigDecimal("100.00"), AccountType.CURRENT, new BigDecimal("20.00"));

    Account domain = AccountJpaMapper.toDomain(entity);

    assertThat(domain).isInstanceOf(CurrentAccount.class);
    assertThat(domain.getAccountId()).isEqualTo("ACC-3");
    assertThat(domain.getBalance()).isEqualByComparingTo(new BigDecimal("100.00"));
    assertThat(((CurrentAccount) domain).getOverdraftLimit())
        .isEqualByComparingTo(new BigDecimal("20.00"));
  }

  @Test
  void should_throw_when_mapping_entity_with_unsupported_type() {
    AccountEntity entity =
        new AccountEntity("ACC-4", new BigDecimal("100.00"), AccountType.SAVINGS, BigDecimal.ZERO);

    assertThatThrownBy(() -> AccountJpaMapper.toDomain(entity))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Unsupported account type");
  }
}
