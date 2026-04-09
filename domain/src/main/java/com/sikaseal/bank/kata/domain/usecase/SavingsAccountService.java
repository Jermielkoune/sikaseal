package com.sikaseal.bank.kata.domain.usecase;

import com.sikaseal.bank.kata.domain.model.SavingsAccount;
import com.sikaseal.bank.kata.domain.ports.api.CreateSavingsAccountApi;
import com.sikaseal.bank.kata.domain.ports.api.DepositOnSavingsAccountApi;
import com.sikaseal.bank.kata.domain.ports.api.WithdrawFromSavingsAccountApi;
import com.sikaseal.bank.kata.domain.ports.spi.SavingsAccountRepositorySpi;
import java.math.BigDecimal;

/** Domain service implementing savings-account-related use cases. */
public class SavingsAccountService
    implements CreateSavingsAccountApi, DepositOnSavingsAccountApi, WithdrawFromSavingsAccountApi {

  private final SavingsAccountRepositorySpi savingsAccountRepository;

  public SavingsAccountService(SavingsAccountRepositorySpi savingsAccountRepository) {
    this.savingsAccountRepository = savingsAccountRepository;
  }

  @Override
  public SavingsAccount createSavingsAccount(
      String accountId, BigDecimal initialBalance, BigDecimal depositCeiling) {
    SavingsAccount savingsAccount = SavingsAccount.of(accountId, initialBalance, depositCeiling);
    return savingsAccountRepository.save(savingsAccount);
  }

  @Override
  public SavingsAccount depositOnSavingsAccount(String accountId, BigDecimal amount) {
    SavingsAccount savingsAccount =
        savingsAccountRepository
            .findById(accountId)
            .orElseThrow(
                () -> new IllegalArgumentException("Savings account not found " + accountId));

    SavingsAccount updatedAccount = savingsAccount.deposit(amount);
    return savingsAccountRepository.save(updatedAccount);
  }

  @Override
  public SavingsAccount withdrawFromSavingsAccount(String accountId, BigDecimal amount) {
    SavingsAccount savingsAccount =
        savingsAccountRepository
            .findById(accountId)
            .orElseThrow(
                () -> new IllegalArgumentException("Savings account not found " + accountId));

    SavingsAccount updatedAccount = savingsAccount.withdraw(amount);
    return savingsAccountRepository.save(updatedAccount);
  }
}
