package com.sikaseal.bank.kata.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OperationTypeTest {

  @Test
  void should_contain_expected_values_when_enum_is_used() {
    assertThat(OperationType.values())
        .containsExactly(OperationType.DEPOSIT, OperationType.WITHDRAW);
  }

  @Test
  void should_resolve_value_by_name_when_value_of_is_called() {
    assertThat(OperationType.valueOf("DEPOSIT")).isEqualTo(OperationType.DEPOSIT);
    assertThat(OperationType.valueOf("WITHDRAW")).isEqualTo(OperationType.WITHDRAW);
  }
}
