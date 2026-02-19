package com.sikaseal.sika_engine;

import static org.assertj.core.api.Assertions.assertThat;

import com.sikaseal.sika_engine.domain.SampleEntity;
import com.sikaseal.sika_engine.domain.SampleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/** Test d'intégration minimal utilisant H2 en mémoire. */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DatabaseIT {

  @Autowired private SampleRepository sampleRepository;

  @Test
  void shouldLoadContext() {
    // Si le contexte ne démarre pas, ce test échouera avant cette ligne.
    assertThat(sampleRepository).isNotNull();
    System.out.println("[IT] DatabaseIT - Contexte 'test' chargé et SampleRepository injecté.");
  }

  @Test
  void shouldPersistEntityInH2() {
    SampleEntity entity = new SampleEntity();
    entity.setName("test-name");

    SampleEntity saved = sampleRepository.save(entity);

    assertThat(saved.getId()).isNotNull();
    assertThat(sampleRepository.findById(saved.getId())).isPresent();
    System.out.println("[IT] DatabaseIT - Entité persistée et retrouvée en H2 avec succès.");
  }
}
