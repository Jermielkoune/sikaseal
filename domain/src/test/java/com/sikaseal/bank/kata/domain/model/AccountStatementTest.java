package com.sikaseal.bank.kata.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class AccountStatementTest {

  @Test
  void should_create_statement_and_expose_immutable_operations_when_factory_is_called() {
    String accountId = "ACC-1";
    AccountType accountType = AccountType.CURRENT;
    LocalDate statementDate = LocalDate.of(2026, 4, 16);
    BigDecimal balanceAtStatementDate = new BigDecimal("123.45");

    Operation op1 =
        Operation.of(
            accountId,
            LocalDateTime.of(2026, 4, 15, 10, 0),
            new BigDecimal("10.00"),
            OperationType.DEPOSIT);

    List<Operation> ops = List.of(op1);

    AccountStatement statement =
        AccountStatement.of(accountId, accountType, statementDate, balanceAtStatementDate, ops);

    assertThat(statement.getAccountId()).isEqualTo(accountId);
    assertThat(statement.getAccountType()).isEqualTo(accountType);
    assertThat(statement.getStatementDate()).isEqualTo(statementDate);
    assertThat(statement.getBalanceAtStatementDate()).isEqualByComparingTo(balanceAtStatementDate);
    assertThat(statement.getOperations()).containsExactly(op1);

    assertThatThrownBy(() -> statement.getOperations().add(op1))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
