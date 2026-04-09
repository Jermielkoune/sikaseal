package com.sikaseal.bank.kata.domain.ports.api;

import com.sikaseal.bank.kata.domain.model.SavingsAccount;
import java.math.BigDecimal;

/** Input port for creating a new savings account. */
public interface CreateSavingsAccountApi {

  /**
   * Creates a new savings account with the given identifier, initial balance and deposit ceiling.
   *
   * @param accountId technical or business identifier of the savings account
   * @param initialBalance initial balance of the savings account
   * @param depositCeiling maximum allowed balance (ceiling) for this savings account
   * @return the created {@link SavingsAccount}
   */
  SavingsAccount createSavingsAccount(
      String accountId, BigDecimal initialBalance, BigDecimal depositCeiling);
}
