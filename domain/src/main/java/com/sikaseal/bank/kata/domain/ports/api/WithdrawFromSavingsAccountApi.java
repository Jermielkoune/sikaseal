package com.sikaseal.bank.kata.domain.ports.api;

import com.sikaseal.bank.kata.domain.model.SavingsAccount;
import java.math.BigDecimal;

/** Input port for withdrawing money from a savings account. */
public interface WithdrawFromSavingsAccountApi {

  /**
   * Withdraws the given amount from the savings account identified by the provided accountId.
   *
   * @param accountId identifier of the savings account
   * @param amount amount to withdraw, must be positive
   * @return the updated {@link SavingsAccount} after the withdrawal
   */
  SavingsAccount withdrawFromSavingsAccount(String accountId, BigDecimal amount);
}
