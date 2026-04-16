package com.sikaseal.bank.kata.domain.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sikaseal.bank.kata.domain.model.SavingsAccount;
import com.sikaseal.bank.kata.domain.ports.spi.SavingsAccountRepositorySpi;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link SavingsAccountService}. */
@ExtendWith(MockitoExtension.class)
class SavingsAccountServiceTest {

  @Mock private SavingsAccountRepositorySpi savingsAccountRepository;

  private SavingsAccountService savingsAccountService;

  @BeforeEach
  void setUp() {
    savingsAccountService = new SavingsAccountService(savingsAccountRepository);
  }

  @Test
  void should_create_savings_account_and_save_it() {
    String accountId = "SAV-1";
    BigDecimal initialBalance = new BigDecimal("100.00");
    BigDecimal ceiling = new BigDecimal("1000.00");

    when(savingsAccountRepository.save(any(SavingsAccount.class)))
        .thenAnswer(invocation -> invocation.getArgument(0, SavingsAccount.class));

    SavingsAccount created =
        savingsAccountService.createSavingsAccount(accountId, initialBalance, ceiling);

    assertThat(created.getAccountId()).isEqualTo(accountId);
    assertThat(created.getBalance()).isEqualByComparingTo(initialBalance);
    assertThat(created.getDepositCeiling()).isEqualByComparingTo(ceiling);

    verify(savingsAccountRepository).save(any(SavingsAccount.class));
  }

  @Test
  void should_deposit_on_existing_savings_account_and_save_updated_account() {
    String accountId = "SAV-2";
    SavingsAccount existing =
        SavingsAccount.of(accountId, new BigDecimal("100.00"), new BigDecimal("1000.00"));

    when(savingsAccountRepository.findById(accountId)).thenReturn(Optional.of(existing));
    when(savingsAccountRepository.save(any(SavingsAccount.class)))
        .thenAnswer(invocation -> invocation.getArgument(0, SavingsAccount.class));

    SavingsAccount updated =
        savingsAccountService.depositOnSavingsAccount(accountId, new BigDecimal("50.00"));

    assertThat(updated.getBalance()).isEqualByComparingTo(new BigDecimal("150.00"));
    verify(savingsAccountRepository).save(any(SavingsAccount.class));
  }

  @Test
  void should_throw_when_depositing_on_unknown_savings_account() {
    when(savingsAccountRepository.findById("UNKNOWN")).thenReturn(Optional.empty());

    assertThatThrownBy(
            () -> savingsAccountService.depositOnSavingsAccount("UNKNOWN", new BigDecimal("10.00")))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Savings account not found");
  }

  @Test
  void should_withdraw_from_existing_savings_account_and_save_updated_account() {
    String accountId = "SAV-3";
    SavingsAccount existing =
        SavingsAccount.of(accountId, new BigDecimal("100.00"), new BigDecimal("1000.00"));

    when(savingsAccountRepository.findById(accountId)).thenReturn(Optional.of(existing));
    when(savingsAccountRepository.save(any(SavingsAccount.class)))
        .thenAnswer(invocation -> invocation.getArgument(0, SavingsAccount.class));

    SavingsAccount updated =
        savingsAccountService.withdrawFromSavingsAccount(accountId, new BigDecimal("40.00"));

    assertThat(updated.getBalance()).isEqualByComparingTo(new BigDecimal("60.00"));
    verify(savingsAccountRepository).save(any(SavingsAccount.class));
  }

  @Test
  void should_throw_when_withdrawing_on_unknown_savings_account() {
    when(savingsAccountRepository.findById("UNKNOWN")).thenReturn(Optional.empty());

    assertThatThrownBy(
            () ->
                savingsAccountService.withdrawFromSavingsAccount(
                    "UNKNOWN", new BigDecimal("10.00")))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Savings account not found");
  }
}
