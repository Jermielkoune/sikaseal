package com.sikaseal.bank.kata.domain.ports.api;

import com.sikaseal.bank.kata.domain.model.SavingsAccount;
import java.math.BigDecimal;

/** Input port for depositing money on a savings account. */
public interface DepositOnSavingsAccountApi {

  /**
   * Deposits the given amount on the savings account identified by the provided accountId.
   *
   * @param accountId identifier of the savings account
   * @param amount amount to deposit, must be positive
   * @return the updated {@link SavingsAccount} after the deposit
   */
  SavingsAccount depositOnSavingsAccount(String accountId, BigDecimal amount);
}
