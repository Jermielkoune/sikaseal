package com.sikaseal.bank.kata.domain.ports.api;

import com.sikaseal.bank.kata.domain.model.Account;
import java.math.BigDecimal;

public interface CreateAccountApi {
  /**
   * Creates a new account with the given identifier and initial balance.
   *
   * @param accountId technical or business identifier of the account
   * @param initialBalance initial balance of the account
   * @return the created {@link Account}
   */
  Account createAccount(String accountId, BigDecimal initialBalance);

  /**
   * Creates a new current account with the given identifier, initial balance and authorized
   * overdraft.
   *
   * <p>This models a current account that may allow the balance to go below zero within the
   * authorized overdraft limit, as described in the business rules of the kata (Feature 2 : le
   * découvert).
   *
   * @param accountId business identifier of the account (e.g. account number)
   * @param initialBalance initial balance of the account
   * @param authorizedOverdraft maximum overdraft amount authorized on this current account
   * @return the created {@link Account}
   */
  Account createCurrentAccountWithOverdraft(
      String accountId, BigDecimal initialBalance, BigDecimal authorizedOverdraft);
}
