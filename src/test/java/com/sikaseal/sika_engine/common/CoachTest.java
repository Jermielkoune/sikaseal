package com.sikaseal.sika_engine.common;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests des implementations de Coach")
class CoachTest {

  @Nested
  @DisplayName("CricketCoach")
  class CricketCoachTest {

    @Test
    @DisplayName("getDailyWorkout retourne le workout de cricket")
    void getDailyWorkout_shouldReturnCricketWorkout() {
      CricketCoach coach = new CricketCoach();
      String workout = coach.getDailyWorkout();
      assertNotNull(workout);
      assertTrue(workout.contains("CricketCoach"));
      assertTrue(workout.contains("bowling"));
    }
  }

  @Nested
  @DisplayName("TrackCoach")
  class TrackCoachTest {

    @Test
    @DisplayName("getDailyWorkout retourne le workout de course")
    void getDailyWorkout_shouldReturnTrackWorkout() {
      TrackCoach coach = new TrackCoach();
      String workout = coach.getDailyWorkout();
      assertNotNull(workout);
      assertTrue(workout.contains("Trackcoach"));
      assertTrue(workout.contains("5k"));
    }
  }

  @Nested
  @DisplayName("BaseballCoach")
  class BaseballCoachTest {

    @Test
    @DisplayName("getDailyWorkout retourne le workout de baseball")
    void getDailyWorkout_shouldReturnBaseballWorkout() {
      BaseballCoach coach = new BaseballCoach();
      String workout = coach.getDailyWorkout();
      assertNotNull(workout);
      assertTrue(workout.contains("Baseball"));
      assertTrue(workout.contains("batting"));
    }
  }

  @Nested
  @DisplayName("SwimCoach")
  class SwimCoachTest {

    @Test
    @DisplayName("getDailyWorkout retourne le workout de natation")
    void getDailyWorkout_shouldReturnSwimWorkout() {
      SwimCoach coach = new SwimCoach();
      String workout = coach.getDailyWorkout();
      assertNotNull(workout);
      assertTrue(workout.contains("Swim"));
      assertTrue(workout.contains("1000"));
    }
  }

  @Nested
  @DisplayName("TennisCoach")
  class TennisCoachTest {

    @Test
    @DisplayName("getDailyWorkout retourne le workout de tennis")
    void getDailyWorkout_shouldReturnTennisWorkout() {
      TennisCoach coach = new TennisCoach();
      String workout = coach.getDailyWorkout();
      assertNotNull(workout);
      assertTrue(workout.contains("Tennis"));
      assertTrue(workout.contains("backhand"));
    }

    @Test
    @DisplayName("doMyStartupStuff ne lance pas d'exception")
    void doMyStartupStuff_shouldNotThrowException() {
      TennisCoach coach = new TennisCoach();
      coach.doMyStartupStuff();
    }

    @Test
    @DisplayName("doMyCleanupStuff ne lance pas d'exception")
    void doMyCleanupStuff_shouldNotThrowException() {
      TennisCoach coach = new TennisCoach();
      coach.doMyCleanupStuff();
    }
  }
}
