package com.sikaseal.bank.kata.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot entry point for the hexagonal bank application.
 *
 * <p>This module is responsible for:
 * <ul>
 *   <li>Bootstrapping the Spring Boot application context</li>
 *   <li>Scanning and wiring together the different modules
 *       (domain, exposition, infrastructure)</li>
 *   <li>Starting the embedded web server when web support is on the classpath</li>
 * </ul>
 *
 * <p>The {@code scanBasePackages} attribute ensures that all components located under
 * {@code com.sikaseal.bank.kata} (in any Maven module) are detected and registered
 * as Spring beans.</p>
 */
@SpringBootApplication(scanBasePackages = "com.sikaseal.bank.kata")
public class LauncherApplication {
  /**
   * Standard Java entry point. Delegates to {@link SpringApplication#run(Class, String...)}
   * to bootstrap the Spring Boot application.
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(String[] args) {
    SpringApplication.run(LauncherApplication.class, args);
  }
}
