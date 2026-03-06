package com.sikaseal.sika_engine;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@DisplayName("Tests de SikaEngineApplication")
@SpringBootTest
class SikaEngineApplicationTest {

  @Autowired private ApplicationContext context;

  @Test
  @DisplayName("Le contexte Spring Boot se charge correctement")
  void contextLoads() {
    assertNotNull(context);
  }

  @Test
  @DisplayName("La methode main demarre l'application")
  void main_shouldStartApplication() {
    SikaEngineApplication.main(new String[] {});
  }
}
