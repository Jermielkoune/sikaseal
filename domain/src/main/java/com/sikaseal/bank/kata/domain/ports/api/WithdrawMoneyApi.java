package com.sikaseal.bank.kata.domain.ports.api;

import com.sikaseal.bank.kata.domain.model.Account;
import java.math.BigDecimal;

/** Port d'entrée pour retirer de l'argent d'un compte. */
public interface WithdrawMoneyApi {

  /**
   * Withdraws the given amount from the account identified by accountId.
   *
   * @param accountId technical or business identifier of the account
   * @param amount amount to withdraw, must be positive
   * @return the updated {@link Account} after the withdrawal
   */
  Account withdraw(String accountId, BigDecimal amount);
}
