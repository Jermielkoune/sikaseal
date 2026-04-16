package com.sikaseal.bank.kata.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sikaseal.bank.kata.domain.model.Account;
import com.sikaseal.bank.kata.domain.model.CurrentAccount;
import com.sikaseal.bank.kata.domain.ports.spi.AccountRepositorySpi;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link AccountService}. */
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock private AccountRepositorySpi accountRepository;

  private AccountService accountService;

  @BeforeEach
  void setUp() {
    accountService = new AccountService(accountRepository);
  }

  @Test
  void should_create_account_and_save_it() {
    String accountId = "ACC-1";
    BigDecimal initialBalance = new BigDecimal("100.00");

    when(accountRepository.save(any(Account.class)))
        .thenAnswer(invocation -> invocation.getArgument(0, Account.class));

    Account created = accountService.createAccount(accountId, initialBalance);

    assertThat(created.getAccountId()).isEqualTo(accountId);
    assertThat(created.getBalance()).isEqualByComparingTo(initialBalance);

    ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
    verify(accountRepository).save(captor.capture());
    assertThat(captor.getValue().getAccountId()).isEqualTo(accountId);
    assertThat(captor.getValue().getBalance()).isEqualByComparingTo(initialBalance);
  }

  @Test
  void should_create_current_account_with_overdraft_and_save_it() {
    String accountId = "ACC-2";
    BigDecimal initialBalance = new BigDecimal("200.00");
    BigDecimal overdraft = new BigDecimal("50.00");

    when(accountRepository.save(any(Account.class)))
        .thenAnswer(invocation -> invocation.getArgument(0, Account.class));

    Account created =
        accountService.createCurrentAccountWithOverdraft(accountId, initialBalance, overdraft);

    assertThat(created).isInstanceOf(CurrentAccount.class);
    assertThat(((CurrentAccount) created).getOverdraftLimit()).isEqualByComparingTo(overdraft);

    ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
    verify(accountRepository).save(captor.capture());
    assertThat(captor.getValue()).isInstanceOf(CurrentAccount.class);
  }

  @Test
  void should_deposit_money_on_existing_account_and_save_updated_account() {
    String accountId = "ACC-3";
    Account existing = CurrentAccount.of(accountId, new BigDecimal("100.00"));

    when(accountRepository.findById(accountId)).thenReturn(Optional.of(existing));
    when(accountRepository.save(any(Account.class)))
        .thenAnswer(invocation -> invocation.getArgument(0, Account.class));

    Account updated = accountService.deposit(accountId, new BigDecimal("30.00"));

    assertThat(updated.getBalance()).isEqualByComparingTo(new BigDecimal("130.00"));
    verify(accountRepository).save(any(Account.class));
  }

  @Test
  void should_throw_when_depositing_on_unknown_account() {
    when(accountRepository.findById("UNKNOWN")).thenReturn(Optional.empty());

    assertThatThrownBy(() -> accountService.deposit("UNKNOWN", new BigDecimal("10.00")))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Account not found");
  }

  @Test
  void should_withdraw_money_on_existing_account_and_save_updated_account() {
    String accountId = "ACC-4";
    Account existing = CurrentAccount.of(accountId, new BigDecimal("100.00"));

    when(accountRepository.findById(accountId)).thenReturn(Optional.of(existing));
    when(accountRepository.save(any(Account.class)))
        .thenAnswer(invocation -> invocation.getArgument(0, Account.class));

    Account updated = accountService.withdraw(accountId, new BigDecimal("40.00"));

    assertThat(updated.getBalance()).isEqualByComparingTo(new BigDecimal("60.00"));
    verify(accountRepository).save(any(Account.class));
  }

  @Test
  void should_throw_when_withdrawing_on_unknown_account() {
    when(accountRepository.findById("UNKNOWN")).thenReturn(Optional.empty());

    assertThatThrownBy(() -> accountService.withdraw("UNKNOWN", new BigDecimal("10.00")))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Account not found");
  }
}
