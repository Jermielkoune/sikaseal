package com.sikaseal.bank.kata.domain.ports.spi;

import com.sikaseal.bank.kata.domain.model.SavingsAccount;
import java.util.Optional;

/** Output port used by the domain to load and persist savings accounts. */
public interface SavingsAccountRepositorySpi {

  /**
   * Loads a {@link SavingsAccount} by its identifier.
   *
   * @param accountId identifier of the savings account
   * @return an {@link Optional} containing the account if found, empty otherwise
   */
  Optional<SavingsAccount> findById(String accountId);

  /**
   * Persists the given {@link SavingsAccount}.
   *
   * @param savingsAccount savings account to persist
   * @return the persisted {@link SavingsAccount}
   */
  SavingsAccount save(SavingsAccount savingsAccount);
}
