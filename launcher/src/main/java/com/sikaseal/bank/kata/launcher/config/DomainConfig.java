package com.sikaseal.bank.kata.launcher.config;

import com.sikaseal.bank.kata.domain.ports.api.CreateAccountApi;
import com.sikaseal.bank.kata.domain.ports.spi.AccountRepositorySpi;
import com.sikaseal.bank.kata.domain.usecase.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configuration Spring du module domain (wiring des use cases). */
@Configuration
public class DomainConfig {

  /**
   * Expose le use case de création de compte via l'API du domaine.
   *
   * @param accountRepositorySpi port SPI permettant l'accès aux données (côté infrastructure)
   * @return l'API du domaine à utiliser par l'exposition
   */
  @Bean
  public CreateAccountApi createAccountApi(AccountRepositorySpi accountRepositorySpi) {
    return new AccountService(accountRepositorySpi);
  }
}
