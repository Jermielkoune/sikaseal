package com.sikaseal.bank.kata.infrastructure.persistence.account;

import org.springframework.data.jpa.repository.JpaRepository;

/** Spring Data repository for {@link AccountEntity}. */
public interface SpringDataAccountRepository extends JpaRepository<AccountEntity, String> {}
