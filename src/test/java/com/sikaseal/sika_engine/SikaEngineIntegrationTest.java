package com.sikaseal.sika_engine;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/** Test d'intégration minimal pour vérifier que le contexte Spring Boot démarre correctement. */
@SpringBootTest
class SikaEngineIntegrationTest {

  @Autowired private ApplicationContext applicationContext;

  @Test
  void shouldLoadContextAndReturnOk() {
    assertThat(applicationContext).isNotNull();
    System.out.println("[IT] SikaEngineIntegrationTest - Contexte chargé sans erreur.");
  }
}
