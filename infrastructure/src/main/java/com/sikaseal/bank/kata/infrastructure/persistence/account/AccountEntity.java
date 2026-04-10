package com.sikaseal.bank.kata.infrastructure.persistence.account;

import com.sikaseal.bank.kata.domain.model.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * JPA entity for persisting accounts.
 *
 * <p>This is an infrastructure concern: it is deliberately separated from domain models.
 */
@Entity
@Table(name = "accounts")
public class AccountEntity {

  @Id
  @Column(name = "account_id", nullable = false, updatable = false)
  private String accountId;

  @Column(name = "balance", nullable = false, precision = 19, scale = 2)
  private BigDecimal balance;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_type", nullable = false)
  private AccountType accountType;

  /** Overdraft limit only applies to current accounts. For savings accounts, it should be zero. */
  @Column(name = "overdraft_limit", nullable = false, precision = 19, scale = 2)
  private BigDecimal overdraftLimit;

  protected AccountEntity() {}

  public AccountEntity(
      String accountId, BigDecimal balance, AccountType accountType, BigDecimal overdraftLimit) {
    this.accountId = accountId;
    this.balance = balance;
    this.accountType = accountType;
    this.overdraftLimit = overdraftLimit;
  }

  public String getAccountId() {
    return accountId;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public BigDecimal getOverdraftLimit() {
    return overdraftLimit;
  }

  public void setOverdraftLimit(BigDecimal overdraftLimit) {
    this.overdraftLimit = overdraftLimit;
  }
}
