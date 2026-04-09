package com.sikaseal.bank.kata.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sikaseal.bank.kata.domain.exception.InsufficientBalanceException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class SavingsAccountTest {

  @Test
  void should_create_savings_account_with_id_balance_and_deposit_ceiling() {
    // Given
    String accountId = "SAV-123";
    BigDecimal initialBalance = new BigDecimal("100.00");
    BigDecimal depositCeiling = new BigDecimal("22950.00");

    // When
    SavingsAccount account = SavingsAccount.of(accountId, initialBalance, depositCeiling);

    // Then
    assertThat(account.getAccountId()).isEqualTo(accountId);
    assertThat(account.getBalance()).isEqualByComparingTo(initialBalance);
    assertThat(account.getDepositCeiling()).isEqualByComparingTo(depositCeiling);
    assertThat(account.getType()).isEqualTo(AccountType.SAVINGS);
  }

  @Test
  void should_increase_balance_when_depositing_money_on_savings_account_within_ceiling() {
    // Given
    String accountId = "SAV-123";
    BigDecimal initialBalance = new BigDecimal("100.00");
    BigDecimal depositCeiling = new BigDecimal("22950.00");
    SavingsAccount account = SavingsAccount.of(accountId, initialBalance, depositCeiling);
    BigDecimal depositAmount = new BigDecimal("50.00");

    // When
    SavingsAccount updatedAccount = account.deposit(depositAmount);

    // Then
    assertThat(updatedAccount.getAccountId()).isEqualTo(accountId);
    assertThat(updatedAccount.getBalance()).isEqualByComparingTo(new BigDecimal("150.00"));
    assertThat(updatedAccount.getDepositCeiling()).isEqualByComparingTo(depositCeiling);
    assertThat(updatedAccount.getType()).isEqualTo(AccountType.SAVINGS);
  }

  @Test
  void should_decrease_balance_when_withdrawing_money_from_savings_account_without_overdraft() {
    // Given
    String accountId = "SAV-123";
    BigDecimal initialBalance = new BigDecimal("100.00");
    BigDecimal depositCeiling = new BigDecimal("22950.00");
    SavingsAccount account = SavingsAccount.of(accountId, initialBalance, depositCeiling);
    BigDecimal withdrawAmount = new BigDecimal("40.00");

    // When
    SavingsAccount updatedAccount = account.withdraw(withdrawAmount);

    // Then
    assertThat(updatedAccount.getAccountId()).isEqualTo(accountId);
    assertThat(updatedAccount.getBalance()).isEqualByComparingTo(new BigDecimal("60.00"));
    assertThat(updatedAccount.getDepositCeiling()).isEqualByComparingTo(depositCeiling);
    assertThat(updatedAccount.getType()).isEqualTo(AccountType.SAVINGS);
  }

  @Test
  void should_not_allow_withdraw_below_zero_on_savings_account() {
    // Given
    String accountId = "SAV-123";
    BigDecimal initialBalance = new BigDecimal("100.00");
    BigDecimal depositCeiling = new BigDecimal("22950.00");
    SavingsAccount account = SavingsAccount.of(accountId, initialBalance, depositCeiling);
    BigDecimal withdrawAmount = new BigDecimal("150.00");

    // When & Then
    assertThatThrownBy(() -> account.withdraw(withdrawAmount))
        .isInstanceOf(InsufficientBalanceException.class);
  }

  @Test
  void should_not_allow_deposit_when_ceiling_would_be_exceeded() {
    // Given
    String accountId = "SAV-123";
    BigDecimal initialBalance = new BigDecimal("900.00");
    BigDecimal depositCeiling = new BigDecimal("1000.00");
    SavingsAccount account = SavingsAccount.of(accountId, initialBalance, depositCeiling);
    BigDecimal depositAmount = new BigDecimal("200.00");

    // When & Then
    assertThatThrownBy(() -> account.deposit(depositAmount))
        .isInstanceOf(
            com.sikaseal.bank.kata.domain.exception.DepositCeilingExceededException.class);
  }
}
