package com.sikaseal.bank.kata.domain.ports.spi;

import com.sikaseal.bank.kata.domain.model.Operation;
import java.time.LocalDateTime;
import java.util.List;

/** Output port used by the domain to load operations for a given account and period. */
public interface OperationRepositorySpi {

  /**
   * Finds all operations for the given account identifier that occurred between the given dates
   * (inclusive).
   *
   * @param accountId identifier of the account
   * @param from start of the period (inclusive)
   * @param to end of the period (inclusive)
   * @return the list of matching operations
   */
  List<Operation> findByAccountIdAndPeriod(String accountId, LocalDateTime from, LocalDateTime to);
}
