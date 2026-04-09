package com.sikaseal.bank.kata.domain.ports.spi;

import com.sikaseal.bank.kata.domain.model.Account;
import java.util.Optional;

/** Output port used by the domain to load and persist accounts. */
public interface AccountRepositorySpi {
  /**
   * Loads an {@link Account} by its identifier.
   *
   * @param accountId identifier of the account
   * @return an {@link Optional} containing the account if found, empty otherwise
   */
  Optional<Account> findById(String accountId);

  /**
   * Persists the given {@link Account}.
   *
   * @param account account to persist
   * @return the persisted {@link Account}
   */
  Account save(Account account);
}
