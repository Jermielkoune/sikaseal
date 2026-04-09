package com.sikaseal.bank.kata.domain.model;

import com.sikaseal.bank.kata.domain.exception.InsufficientBalanceException;
import java.math.BigDecimal;

/** Compte courant avec éventuel découvert autorisé. */
public class CurrentAccount extends Account {

  private final BigDecimal overdraftLimit;

  private CurrentAccount(String accountId, BigDecimal balance, BigDecimal overdraftLimit) {
    super(accountId, balance, AccountType.CURRENT);
    this.overdraftLimit = overdraftLimit;
  }

  public static CurrentAccount of(String accountId, BigDecimal balance) {
    return new CurrentAccount(accountId, balance, BigDecimal.ZERO);
  }

  public static CurrentAccount of(String accountId, BigDecimal balance, BigDecimal overdraftLimit) {
    return new CurrentAccount(accountId, balance, overdraftLimit);
  }

  public BigDecimal getOverdraftLimit() {
    return overdraftLimit;
  }

  @Override
  public Account deposit(BigDecimal amount) {
    BigDecimal newBalance = getBalance().add(amount);
    return new CurrentAccount(getAccountId(), newBalance, overdraftLimit);
  }

  @Override
  public Account withdraw(BigDecimal amount) {
    BigDecimal newBalance = getBalance().subtract(amount);
    BigDecimal allowedMinimumBalance = overdraftLimit.negate();
    if (newBalance.compareTo(allowedMinimumBalance) < 0) {
      throw new InsufficientBalanceException(
          "Cannot withdraw "
              + amount
              + " from account "
              + getAccountId()
              + " with balance "
              + getBalance()
              + " and overdraft limit "
              + overdraftLimit);
    }
    return new CurrentAccount(getAccountId(), newBalance, overdraftLimit);
  }
}
