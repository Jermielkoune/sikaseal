package com.sikaseal.bank.kata.infrastructure.persistence.account;

import com.sikaseal.bank.kata.domain.model.Account;
import com.sikaseal.bank.kata.domain.model.AccountType;
import com.sikaseal.bank.kata.domain.model.CurrentAccount;
import java.math.BigDecimal;

/** Mapper between the domain model {@link Account} and the JPA {@link AccountEntity}. */
final class AccountJpaMapper {

  private AccountJpaMapper() {}

  static AccountEntity toEntity(Account account) {
    BigDecimal overdraftLimit = BigDecimal.ZERO;
    if (account instanceof CurrentAccount currentAccount) {
      overdraftLimit = currentAccount.getOverdraftLimit();
    }

    return new AccountEntity(
        account.getAccountId(), account.getBalance(), account.getType(), overdraftLimit);
  }

  static Account toDomain(AccountEntity entity) {
    if (entity.getAccountType() == AccountType.CURRENT) {
      return CurrentAccount.of(
          entity.getAccountId(), entity.getBalance(), entity.getOverdraftLimit());
    }

    // Savings account persistence will be handled by a dedicated repository when feature 3 is
    // wired.
    throw new IllegalStateException(
        "Unsupported account type for AccountRepositorySpi: " + entity.getAccountType());
  }
}
