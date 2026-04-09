package com.sikaseal.bank.kata.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/** Represents a monthly account statement for a given bank account. */
public class AccountStatement {

  private final String accountId;
  private final AccountType accountType;
  private final LocalDate statementDate;
  private final BigDecimal balanceAtStatementDate;
  private final List<Operation> operations;

  private AccountStatement(
      String accountId,
      AccountType accountType,
      LocalDate statementDate,
      BigDecimal balanceAtStatementDate,
      List<Operation> operations) {
    this.accountId = accountId;
    this.accountType = accountType;
    this.statementDate = statementDate;
    this.balanceAtStatementDate = balanceAtStatementDate;
    this.operations = List.copyOf(operations);
  }

  public static AccountStatement of(
      String accountId,
      AccountType accountType,
      LocalDate statementDate,
      BigDecimal balanceAtStatementDate,
      List<Operation> operations) {
    return new AccountStatement(
        accountId, accountType, statementDate, balanceAtStatementDate, operations);
  }

  public String getAccountId() {
    return accountId;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public LocalDate getStatementDate() {
    return statementDate;
  }

  public BigDecimal getBalanceAtStatementDate() {
    return balanceAtStatementDate;
  }

  public List<Operation> getOperations() {
    return Collections.unmodifiableList(operations);
  }
}
