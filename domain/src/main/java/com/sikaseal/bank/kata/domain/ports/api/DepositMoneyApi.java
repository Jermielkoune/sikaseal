package com.sikaseal.bank.kata.domain.ports.api;

import com.sikaseal.bank.kata.domain.model.Account;
import java.math.BigDecimal;

public interface DepositMoneyApi {

  /**
   * Deposits the given amount on the account identified by accountId.
   *
   * @param accountId technical or business identifier of the account
   * @param amount amount to deposit, must be positive
   * @return the updated {@link Account} after the deposit
   */
  Account deposit(String accountId, BigDecimal amount);
}
