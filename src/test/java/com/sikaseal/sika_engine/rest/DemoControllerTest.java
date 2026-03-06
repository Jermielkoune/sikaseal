package com.sikaseal.sika_engine.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.sikaseal.sika_engine.common.Coach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Test unitaire pour DemoController. */
@ExtendWith(MockitoExtension.class)
class DemoControllerTest {

  @Mock private Coach mockCoach;

  private DemoController demoController;

  @BeforeEach
  void setUp() {
    demoController = new DemoController(mockCoach);
  }

  @Test
  @DisplayName("getDailyWorkout retourne le workout du coach")
  void getDailyWorkout_shouldReturnCoachWorkout() {
    // Arrange
    String expectedWorkout = "Run 5 kilometers";
    when(mockCoach.getDailyWorkout()).thenReturn(expectedWorkout);

    // Act
    String result = demoController.getDailyWorkout();

    // Assert
    assertNotNull(result);
    assertEquals(expectedWorkout, result);
  }

  @Test
  @DisplayName("Le controller est correctement initialisé avec un coach")
  void constructor_shouldInitializeWithCoach() {
    // Assert
    assertNotNull(demoController);
  }
}
