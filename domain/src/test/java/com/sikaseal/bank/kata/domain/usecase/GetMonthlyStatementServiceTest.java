package com.sikaseal.bank.kata.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sikaseal.bank.kata.domain.model.*;
import com.sikaseal.bank.kata.domain.ports.spi.AccountRepositorySpi;
import com.sikaseal.bank.kata.domain.ports.spi.OperationRepositorySpi;
import com.sikaseal.bank.kata.domain.ports.spi.SavingsAccountRepositorySpi;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link GetMonthlyStatementService}. */
@ExtendWith(MockitoExtension.class)
class GetMonthlyStatementServiceTest {

  @Mock private AccountRepositorySpi accountRepository;
  @Mock private SavingsAccountRepositorySpi savingsAccountRepository;
  @Mock private OperationRepositorySpi operationRepository;

  private GetMonthlyStatementService service;

  @BeforeEach
  void setUp() {
    service =
        new GetMonthlyStatementService(
            accountRepository, savingsAccountRepository, operationRepository);
  }

  @Test
  void should_build_statement_for_current_account_and_sort_operations_desc() {
    String accountId = "ACC-10";
    LocalDate statementDate = LocalDate.of(2026, 4, 16);

    Account account = CurrentAccount.of(accountId, new BigDecimal("120.00"));
    when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

    LocalDateTime t1 = statementDate.minusDays(2).atTime(10, 0);
    LocalDateTime t2 = statementDate.minusDays(1).atTime(9, 0);

    List<Operation> ops =
        new ArrayList<>(
            List.of(
                Operation.of(accountId, t1, new BigDecimal("10.00"), OperationType.DEPOSIT),
                Operation.of(accountId, t2, new BigDecimal("5.00"), OperationType.WITHDRAW)));

    when(operationRepository.findByAccountIdAndPeriod(eq(accountId), any(), any())).thenReturn(ops);

    AccountStatement statement = service.getMonthlyStatement(accountId, statementDate);

    assertThat(statement.getAccountId()).isEqualTo(accountId);
    assertThat(statement.getAccountType()).isEqualTo(AccountType.CURRENT);
    assertThat(statement.getStatementDate()).isEqualTo(statementDate);
    assertThat(statement.getBalanceAtStatementDate())
        .isEqualByComparingTo(new BigDecimal("120.00"));

    // tri décroissant attendu: t2 puis t1
    assertThat(statement.getOperations()).hasSize(2);
    assertThat(statement.getOperations().get(0).getDateTime()).isEqualTo(t2);
    assertThat(statement.getOperations().get(1).getDateTime()).isEqualTo(t1);

    verify(operationRepository).findByAccountIdAndPeriod(eq(accountId), any(), any());
  }

  @Test
  void should_build_statement_for_savings_account_when_current_not_found() {
    String accountId = "SAV-10";
    LocalDate statementDate = LocalDate.of(2026, 4, 16);

    when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

    SavingsAccount savings =
        SavingsAccount.of(accountId, new BigDecimal("999.00"), new BigDecimal("2000.00"));
    when(savingsAccountRepository.findById(accountId)).thenReturn(Optional.of(savings));

    when(operationRepository.findByAccountIdAndPeriod(eq(accountId), any(), any()))
        .thenReturn(new ArrayList<>());

    AccountStatement statement = service.getMonthlyStatement(accountId, statementDate);

    assertThat(statement.getAccountType()).isEqualTo(AccountType.SAVINGS);
    assertThat(statement.getBalanceAtStatementDate())
        .isEqualByComparingTo(new BigDecimal("999.00"));
  }

  @Test
  void should_throw_when_account_not_found_in_both_repositories() {
    String accountId = "MISSING";
    LocalDate statementDate = LocalDate.of(2026, 4, 16);

    when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
    when(savingsAccountRepository.findById(accountId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getMonthlyStatement(accountId, statementDate))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Account not found");
  }
}
