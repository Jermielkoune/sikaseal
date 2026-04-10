package com.sikaseal.bank.kata.launcher.config;

import com.sikaseal.bank.kata.domain.ports.api.CreateAccountApi;
import com.sikaseal.bank.kata.domain.ports.spi.AccountRepositorySpi;
import com.sikaseal.bank.kata.domain.usecase.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
  @Bean
  public CreateAccountApi createAccountApi(AccountRepositorySpi accountRepositorySpi) {
    return new AccountService(accountRepositorySpi);
  }
}
