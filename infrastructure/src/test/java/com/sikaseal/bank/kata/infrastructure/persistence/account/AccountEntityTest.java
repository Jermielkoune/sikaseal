package com.sikaseal.bank.kata.infrastructure.persistence.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.sikaseal.bank.kata.domain.model.AccountType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class AccountEntityTest {

  @Test
  void should_expose_and_update_fields_when_using_getters_and_setters() {
    AccountEntity entity =
        new AccountEntity(
            "ACC-1", new BigDecimal("100.00"), AccountType.CURRENT, new BigDecimal("10.00"));

    assertThat(entity.getAccountId()).isEqualTo("ACC-1");
    assertThat(entity.getBalance()).isEqualByComparingTo(new BigDecimal("100.00"));
    assertThat(entity.getAccountType()).isEqualTo(AccountType.CURRENT);
    assertThat(entity.getOverdraftLimit()).isEqualByComparingTo(new BigDecimal("10.00"));

    entity.setBalance(new BigDecimal("200.00"));
    entity.setAccountType(AccountType.SAVINGS);
    entity.setOverdraftLimit(BigDecimal.ZERO);

    assertThat(entity.getBalance()).isEqualByComparingTo(new BigDecimal("200.00"));
    assertThat(entity.getAccountType()).isEqualTo(AccountType.SAVINGS);
    assertThat(entity.getOverdraftLimit()).isEqualByComparingTo(BigDecimal.ZERO);
  }
}
