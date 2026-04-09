package com.sikaseal.bank.kata.domain.usecase;

import com.sikaseal.bank.kata.domain.model.*;
import com.sikaseal.bank.kata.domain.ports.api.GetMonthlyStatementApi;
import com.sikaseal.bank.kata.domain.ports.spi.AccountRepositorySpi;
import com.sikaseal.bank.kata.domain.ports.spi.OperationRepositorySpi;
import com.sikaseal.bank.kata.domain.ports.spi.SavingsAccountRepositorySpi;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/** Domain service responsible for building monthly account statements. */
public class GetMonthlyStatementService implements GetMonthlyStatementApi {

  private final AccountRepositorySpi accountRepository;
  private final SavingsAccountRepositorySpi savingsAccountRepository;
  private final OperationRepositorySpi operationRepository;

  public GetMonthlyStatementService(
      AccountRepositorySpi accountRepository,
      SavingsAccountRepositorySpi savingsAccountRepository,
      OperationRepositorySpi operationRepository) {
    this.accountRepository = accountRepository;
    this.savingsAccountRepository = savingsAccountRepository;
    this.operationRepository = operationRepository;
  }

  @Override
  public AccountStatement getMonthlyStatement(String accountId, LocalDate statementDate) {
    // Determine account type and current balance
    AccountType accountType;
    BigDecimal balance;

    Account account = accountRepository.findById(accountId).orElse(null);

    if (account != null) {
      accountType = account.getType();
      balance = account.getBalance();
    } else {
      SavingsAccount savingsAccount =
          savingsAccountRepository
              .findById(accountId)
              .orElseThrow(() -> new IllegalArgumentException("Account not found " + accountId));
      accountType = savingsAccount.getType();
      balance = savingsAccount.getBalance();
    }

    // Compute sliding month period
    LocalDate fromDate = statementDate.minusMonths(1);
    LocalDateTime fromDateTime = fromDate.atStartOfDay();
    LocalDateTime toDateTime = statementDate.plusDays(1).atStartOfDay().minusNanos(1);

    List<Operation> operations =
        operationRepository.findByAccountIdAndPeriod(accountId, fromDateTime, toDateTime);
    operations.sort(Comparator.comparing(Operation::getDateTime).reversed());

    return AccountStatement.of(accountId, accountType, statementDate, balance, operations);
  }
}
