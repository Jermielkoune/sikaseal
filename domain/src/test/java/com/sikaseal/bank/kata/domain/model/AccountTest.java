package com.sikaseal.bank.kata.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sikaseal.bank.kata.domain.exception.InsufficientBalanceException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class AccountTest {

  @Test
  void should_create_current_account_with_id_and_initial_balance() {
    // GIVEN
    String accountId = "ACC-123";
    BigDecimal initialBalance = new BigDecimal("500.00");

    // WHEN
    Account account = CurrentAccount.of(accountId, initialBalance);

    // THEN
    assertThat(account.getAccountId()).isEqualTo(accountId);
    assertThat(account.getBalance()).isEqualByComparingTo(initialBalance);
    assertThat(((CurrentAccount) account).getOverdraftLimit())
        .isEqualByComparingTo(BigDecimal.ZERO);
    assertThat(account.getType()).isEqualTo(AccountType.CURRENT);
  }

  @Test
  void should_increase_balance_when_depositing_money_on_current_account() {
    // GIVEN
    String accountId = "ACC-123";
    BigDecimal balance = new BigDecimal("100.00");

    Account account = CurrentAccount.of(accountId, balance);

    BigDecimal depositAmount = new BigDecimal("50.00");

    // WHEN
    Account updatedAccount = account.deposit(depositAmount);

    // THEN
    assertThat(updatedAccount.getAccountId()).isEqualTo(accountId);
    assertThat(updatedAccount.getBalance()).isEqualByComparingTo(new BigDecimal("150.00"));
    assertThat(((CurrentAccount) updatedAccount).getOverdraftLimit())
        .isEqualByComparingTo(BigDecimal.ZERO);
  }

  @Test
  void should_decrease_balance_when_withdrawing_money_from_current_account() {
    // Given
    String accountId = "ACC-123";
    BigDecimal initialBalance = new BigDecimal("100.00");

    Account account = CurrentAccount.of(accountId, initialBalance);

    BigDecimal withdrawAmount = new BigDecimal("40.00");

    // When
    Account updatedAccount = account.withdraw(withdrawAmount);

    // Then
    assertThat(updatedAccount.getAccountId()).isEqualTo(accountId);
    assertThat(updatedAccount.getBalance()).isEqualByComparingTo(new BigDecimal("60.00"));
    assertThat(((CurrentAccount) updatedAccount).getOverdraftLimit())
        .isEqualByComparingTo(BigDecimal.ZERO);
  }

  @Test
  void should_not_allow_withdraw_when_amount_is_greater_than_balance_and_no_overdraft() {
    // Given
    String accountId = "ACC-123";
    BigDecimal initialBalance = new BigDecimal("100.00");

    Account account = CurrentAccount.of(accountId, initialBalance);

    BigDecimal withdrawAmount = new BigDecimal("150.00");

    // When & Then
    assertThatThrownBy(() -> account.withdraw(withdrawAmount))
        .isInstanceOf(InsufficientBalanceException.class);
  }

  @Test
  void should_allow_withdrawal_within_overdraft_limit_on_current_account() {
    // Given
    String accountId = "ACC-OVERDRAFT";
    BigDecimal initialBalance = new BigDecimal("100.00");
    BigDecimal overdraftLimit = new BigDecimal("200.00");

    Account account = CurrentAccount.of(accountId, initialBalance, overdraftLimit);

    BigDecimal withdrawAmount = new BigDecimal("250.00"); // final balance = -150, within -200

    // When
    Account updatedAccount = account.withdraw(withdrawAmount);

    // Then
    assertThat(updatedAccount.getBalance()).isEqualByComparingTo(new BigDecimal("-150.00"));
    assertThat(((CurrentAccount) updatedAccount).getOverdraftLimit())
        .isEqualByComparingTo(overdraftLimit);
  }

  @Test
  void should_not_allow_withdrawal_beyond_overdraft_limit_on_current_account() {
    // Given
    String accountId = "ACC-OVERDRAFT";
    BigDecimal initialBalance = new BigDecimal("100.00");
    BigDecimal overdraftLimit = new BigDecimal("200.00");
    Account account = CurrentAccount.of(accountId, initialBalance, overdraftLimit);
    BigDecimal withdrawAmount = new BigDecimal("350.00"); // final balance = -250, below -200

    // When & Then
    assertThatThrownBy(() -> account.withdraw(withdrawAmount))
        .isInstanceOf(InsufficientBalanceException.class);
  }
}
