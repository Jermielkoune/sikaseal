package com.sikaseal.bank.kata.domain.usecase;

import com.sikaseal.bank.kata.domain.model.Account;
import com.sikaseal.bank.kata.domain.model.CurrentAccount;
import com.sikaseal.bank.kata.domain.ports.api.CreateAccountApi;
import com.sikaseal.bank.kata.domain.ports.api.DepositMoneyApi;
import com.sikaseal.bank.kata.domain.ports.api.WithdrawMoneyApi;
import com.sikaseal.bank.kata.domain.ports.spi.AccountRepositorySpi;
import java.math.BigDecimal;

/** Domain service implementing account-related use cases. */
public class AccountService implements CreateAccountApi, DepositMoneyApi, WithdrawMoneyApi {

  private final AccountRepositorySpi accountRepository;

  public AccountService(AccountRepositorySpi accountRepositorySpi) {
    this.accountRepository = accountRepositorySpi;
  }

  @Override
  public Account createAccount(String accountId, BigDecimal initialBalance) {
    Account account = CurrentAccount.of(accountId, initialBalance);
    return accountRepository.save(account);
  }

  @Override
  public Account createCurrentAccountWithOverdraft(
      String accountId, BigDecimal initialBalance, BigDecimal authorizedOverdraft) {
    Account account = CurrentAccount.of(accountId, initialBalance, authorizedOverdraft);
    return accountRepository.save(account);
  }

  @Override
  public Account deposit(String accountId, BigDecimal amount) {
    Account account =
        accountRepository
            .findById(accountId)
            .orElseThrow(() -> new IllegalArgumentException("Account not found " + accountId));

    Account updatedAccount = account.deposit(amount);

    return accountRepository.save(updatedAccount);
  }

  @Override
  public Account withdraw(String accountId, BigDecimal amount) {
    Account account =
        accountRepository
            .findById(accountId)
            .orElseThrow(() -> new IllegalArgumentException("Account not found " + accountId));

    Account updatedAccount = account.withdraw(amount);

    return accountRepository.save(updatedAccount);
  }
}
