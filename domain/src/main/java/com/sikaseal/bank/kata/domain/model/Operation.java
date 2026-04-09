package com.sikaseal.bank.kata.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Represents a single operation performed on a bank account. */
public class Operation {

  private final String accountId;
  private final LocalDateTime dateTime;
  private final BigDecimal amount;
  private final OperationType type;

  private Operation(
      String accountId, LocalDateTime dateTime, BigDecimal amount, OperationType type) {
    this.accountId = accountId;
    this.dateTime = dateTime;
    this.amount = amount;
    this.type = type;
  }

  public static Operation of(
      String accountId, LocalDateTime dateTime, BigDecimal amount, OperationType type) {
    return new Operation(accountId, dateTime, amount, type);
  }

  public String getAccountId() {
    return accountId;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public OperationType getType() {
    return type;
  }
}
