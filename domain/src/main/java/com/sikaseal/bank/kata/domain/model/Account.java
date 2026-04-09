package com.sikaseal.bank.kata.domain.model;

import java.math.BigDecimal;

/** Base abstraite pour tous les types de compte bancaire. */
public abstract class Account {

  private final String accountId;
  private final BigDecimal balance;
  private final AccountType type;

  protected Account(String accountId, BigDecimal balance, AccountType type) {
    this.accountId = accountId;
    this.balance = balance;
    this.type = type;
  }

  public String getAccountId() {
    return accountId;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public AccountType getType() {
    return type;
  }

  /** Effectue un dépôt et retourne un nouveau compte avec le solde mis à jour. */
  public abstract Account deposit(BigDecimal amount);

  /** Effectue un retrait et retourne un nouveau compte avec le solde mis à jour. */
  public abstract Account withdraw(BigDecimal amount);
}
