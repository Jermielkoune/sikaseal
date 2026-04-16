package com.sikaseal.bank.kata.exposition.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sikaseal.bank.kata.domain.model.Account;
import com.sikaseal.bank.kata.domain.model.CurrentAccount;
import com.sikaseal.bank.kata.domain.ports.api.CreateAccountApi;
import com.sikaseal.bank.kata.exposition.rest.dto.CreateCurrentAccountRequestDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

  @Mock private CreateAccountApi createAccountApi;

  @Test
  void should_create_current_account_without_overdraft_when_authorized_overdraft_is_null() {
    AccountController controller = new AccountController(createAccountApi);

    String accountId = "ACC-1";
    BigDecimal initialBalance = new BigDecimal("100.00");

    CreateCurrentAccountRequestDTO request =
        new CreateCurrentAccountRequestDTO(accountId, initialBalance, null);

    Account created = CurrentAccount.of(accountId, initialBalance);
    when(createAccountApi.createAccount(accountId, initialBalance)).thenReturn(created);

    ResponseEntity<?> response = controller.createCurrentAccount(request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    verify(createAccountApi).createAccount(accountId, initialBalance);
  }

  @Test
  void should_create_current_account_with_overdraft_when_authorized_overdraft_is_provided() {
    AccountController controller = new AccountController(createAccountApi);

    String accountId = "ACC-2";
    BigDecimal initialBalance = new BigDecimal("100.00");
    BigDecimal overdraft = new BigDecimal("50.00");

    CreateCurrentAccountRequestDTO request =
        new CreateCurrentAccountRequestDTO(accountId, initialBalance, overdraft);

    Account created = CurrentAccount.of(accountId, initialBalance, overdraft);
    when(createAccountApi.createCurrentAccountWithOverdraft(accountId, initialBalance, overdraft))
        .thenReturn(created);

    ResponseEntity<?> response = controller.createCurrentAccount(request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    verify(createAccountApi)
        .createCurrentAccountWithOverdraft(accountId, initialBalance, overdraft);
  }

  @Test
  void should_set_overdraft_limit_to_zero_when_created_account_is_not_a_current_account() {
    AccountController controller = new AccountController(createAccountApi);

    String accountId = "ACC-3";
    BigDecimal initialBalance = new BigDecimal("100.00");

    CreateCurrentAccountRequestDTO request =
        new CreateCurrentAccountRequestDTO(accountId, initialBalance, null);

    // Fake Account, not instance of CurrentAccount -> hits the else branch for overdraftLimit
    Account notCurrent =
        new Account(
            accountId, initialBalance, com.sikaseal.bank.kata.domain.model.AccountType.SAVINGS) {
          @Override
          public Account deposit(BigDecimal amount) {
            return this;
          }

          @Override
          public Account withdraw(BigDecimal amount) {
            return this;
          }
        };

    when(createAccountApi.createAccount(any(), any())).thenReturn(notCurrent);

    ResponseEntity<?> response = controller.createCurrentAccount(request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody())
        .isInstanceOf(com.sikaseal.bank.kata.exposition.rest.dto.AccountResponseDTO.class);
    com.sikaseal.bank.kata.exposition.rest.dto.AccountResponseDTO body =
        (com.sikaseal.bank.kata.exposition.rest.dto.AccountResponseDTO) response.getBody();
    assertThat(body.authorizedOverdraft()).isEqualByComparingTo(BigDecimal.ZERO);
  }
}
