package com.sikaseal.bank.kata.infrastructure.persistence.account;

import com.sikaseal.bank.kata.domain.model.Account;
import com.sikaseal.bank.kata.domain.ports.spi.AccountRepositorySpi;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Driven adapter implementing the domain output port {@link AccountRepositorySpi} using Spring Data
 * JPA.
 */
@Repository
@Transactional
public class AccountRepositoryAdapter implements AccountRepositorySpi {

  private final SpringDataAccountRepository repository;

  public AccountRepositoryAdapter(SpringDataAccountRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Account> findById(String accountId) {
    return repository.findById(accountId).map(AccountJpaMapper::toDomain);
  }

  @Override
  public Account save(Account account) {
    AccountEntity saved = repository.save(AccountJpaMapper.toEntity(account));
    return AccountJpaMapper.toDomain(saved);
  }
}
