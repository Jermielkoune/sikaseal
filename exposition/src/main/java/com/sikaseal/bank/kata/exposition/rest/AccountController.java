package com.sikaseal.bank.kata.exposition.rest;

import com.sikaseal.bank.kata.domain.model.Account;
import com.sikaseal.bank.kata.domain.model.CurrentAccount;
import com.sikaseal.bank.kata.domain.ports.api.CreateAccountApi;
import com.sikaseal.bank.kata.exposition.rest.dto.AccountResponseDTO;
import com.sikaseal.bank.kata.exposition.rest.dto.CreateCurrentAccountRequestDTO;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST driving adapter exposing account creation use cases.
 *
 * <p>This controller maps HTTP requests to the {@link CreateAccountApi} domain port. It currently
 * handles the creation of current accounts, with or without an authorized overdraft, according to
 * the JSON payload received.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

  private final CreateAccountApi createAccountApi;

  /**
   * Creates a new {@link AccountController} with the given domain port.
   *
   * @param createAccountApi domain port used to create accounts in the core banking domain
   */
  public AccountController(CreateAccountApi createAccountApi) {
    this.createAccountApi = createAccountApi;
  }

  /**
   * Creates a current account based on the provided JSON payload.
   *
   * <p>Overdraft semantics:
   *
   * <ul>
   *   <li>If {@code authorizedOverdraft} is {@code null}, a current account without authorized
   *       overdraft is created.
   *   <li>If {@code authorizedOverdraft} is provided, a current account with the given overdraft
   *       limit is created.
   * </ul>
   *
   * @param request JSON payload describing the account to create
   * @return {@link ResponseEntity} containing the created account summary
   */
  @PostMapping("/current")
  public ResponseEntity<AccountResponseDTO> createCurrentAccount(
      @Valid @RequestBody CreateCurrentAccountRequestDTO request) {

    String accountId = request.accountId();
    BigDecimal initialBalance = request.initialBalance();
    BigDecimal authorizedOverdraft = request.authorizedOverdraft();

    Account createdAccount;
    if (authorizedOverdraft == null) {
      // current account without overdraft
      createdAccount = createAccountApi.createAccount(accountId, initialBalance);
    } else {
      // current account with overdraft
      createdAccount =
          createAccountApi.createCurrentAccountWithOverdraft(
              accountId, initialBalance, authorizedOverdraft);
    }

    BigDecimal overdraftLimit = BigDecimal.ZERO;
    if (createdAccount instanceof CurrentAccount current) {
      overdraftLimit = current.getOverdraftLimit();
    }

    AccountResponseDTO response =
        new AccountResponseDTO(
            createdAccount.getAccountId(),
            createdAccount.getBalance(),
            createdAccount.getType().name(),
            overdraftLimit);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
