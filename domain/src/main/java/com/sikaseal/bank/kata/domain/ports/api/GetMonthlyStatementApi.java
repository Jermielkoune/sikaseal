package com.sikaseal.bank.kata.domain.ports.api;

import com.sikaseal.bank.kata.domain.model.AccountStatement;
import java.time.LocalDate;

/** Input port for retrieving a monthly account statement (sliding month) for a given account. */
public interface GetMonthlyStatementApi {

  /**
   * Returns the monthly account statement for the given account on the given statement date. The
   * covered period is the sliding month ending at the statement date.
   *
   * @param accountId identifier of the account
   * @param statementDate date at which the statement is emitted
   * @return the corresponding {@link AccountStatement}
   */
  AccountStatement getMonthlyStatement(String accountId, LocalDate statementDate);
}
