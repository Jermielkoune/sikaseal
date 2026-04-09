package com.sikaseal.bank.kata.domain.model;

import com.sikaseal.bank.kata.domain.exception.DepositCeilingExceededException;
import com.sikaseal.bank.kata.domain.exception.InsufficientBalanceException;
import java.math.BigDecimal;

/** Compte d'épargne avec plafond de dépôt. */
public class SavingsAccount extends Account {

  private final BigDecimal depositCeiling;

  private SavingsAccount(String accountId, BigDecimal balance, BigDecimal depositCeiling) {
    super(accountId, balance, AccountType.SAVINGS);
    this.depositCeiling = depositCeiling;
  }

  public static SavingsAccount of(
      String accountId, BigDecimal initialBalance, BigDecimal depositCeiling) {
    return new SavingsAccount(accountId, initialBalance, depositCeiling);
  }

  public BigDecimal getDepositCeiling() {
    return depositCeiling;
  }

  @Override
  public SavingsAccount deposit(BigDecimal amount) {
    BigDecimal newBalance = getBalance().add(amount);
    if (newBalance.compareTo(this.depositCeiling) > 0) {
      throw new DepositCeilingExceededException(
          "Cannot deposit "
              + amount
              + " on account "
              + getAccountId()
              + " with balance "
              + getBalance()
              + " and ceiling "
              + depositCeiling);
    }
    return new SavingsAccount(getAccountId(), newBalance, this.depositCeiling);
  }

  @Override
  public SavingsAccount withdraw(BigDecimal amount) {
    BigDecimal newBalance = getBalance().subtract(amount);
    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
      throw new InsufficientBalanceException(
          "Cannot withdraw "
              + amount
              + " from savings account "
              + getAccountId()
              + " with balance "
              + getBalance());
    }
    return new SavingsAccount(getAccountId(), newBalance, this.depositCeiling);
  }
}
