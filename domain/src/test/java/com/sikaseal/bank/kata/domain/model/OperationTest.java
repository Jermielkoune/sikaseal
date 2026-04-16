package com.sikaseal.bank.kata.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class OperationTest {

  @Test
  void should_create_withdraw_operation_and_expose_all_fields_when_factory_is_called() {
    String accountId = "ACC-1";
    LocalDateTime dateTime = LocalDateTime.of(2026, 4, 16, 12, 30);
    BigDecimal amount = new BigDecimal("42.00");

    Operation operation = Operation.of(accountId, dateTime, amount, OperationType.WITHDRAW);

    assertThat(operation.getAccountId()).isEqualTo(accountId);
    assertThat(operation.getDateTime()).isEqualTo(dateTime);
    assertThat(operation.getAmount()).isEqualByComparingTo(amount);
    assertThat(operation.getType()).isEqualTo(OperationType.WITHDRAW);
  }

  @Test
  void should_create_deposit_operation_and_expose_all_fields_when_factory_is_called() {
    String accountId = "ACC-2";
    LocalDateTime dateTime = LocalDateTime.of(2026, 4, 16, 8, 15);
    BigDecimal amount = new BigDecimal("10.00");

    Operation operation = Operation.of(accountId, dateTime, amount, OperationType.DEPOSIT);

    assertThat(operation.getAccountId()).isEqualTo(accountId);
    assertThat(operation.getDateTime()).isEqualTo(dateTime);
    assertThat(operation.getAmount()).isEqualByComparingTo(amount);
    assertThat(operation.getType()).isEqualTo(OperationType.DEPOSIT);
  }

  @Test
  void should_allow_zero_amount_and_null_checks_for_robustness() {
    String accountId = "ACC-3";
    LocalDateTime dateTime = LocalDateTime.of(2026, 4, 16, 15, 45);
    BigDecimal zeroAmount = new BigDecimal("0.00");

    Operation operation = Operation.of(accountId, dateTime, zeroAmount, OperationType.WITHDRAW);

    assertThat(operation.getAmount()).isEqualByComparingTo(BigDecimal.ZERO);
    assertThat(operation.getDateTime().toLocalDate().getYear()).isEqualTo(2026);
  }
}
