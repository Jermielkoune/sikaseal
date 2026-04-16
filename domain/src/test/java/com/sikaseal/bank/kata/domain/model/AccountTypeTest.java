package com.sikaseal.bank.kata.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AccountTypeTest {

  @Test
  void should_contain_expected_values_when_enum_is_used() {
    assertThat(AccountType.values()).containsExactly(AccountType.CURRENT, AccountType.SAVINGS);
  }

  @Test
  void should_resolve_value_by_name_when_value_of_is_called() {
    assertThat(AccountType.valueOf("CURRENT")).isEqualTo(AccountType.CURRENT);
    assertThat(AccountType.valueOf("SAVINGS")).isEqualTo(AccountType.SAVINGS);
  }
}
