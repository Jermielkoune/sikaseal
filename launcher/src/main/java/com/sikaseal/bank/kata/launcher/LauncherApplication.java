package com.sikaseal.bank.kata.launcher;

import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

/**
 * Main Spring Boot entry point for the hexagonal bank application.
 *
 * <p>This module is responsible for:
 *
 * <ul>
 *   <li>Bootstrapping the Spring Boot application context
 *   <li>Scanning and wiring together the different modules (domain, exposition, infrastructure)
 *   <li>Starting the embedded web server when web support is on the classpath
 * </ul>
 *
 * <p>The {@code scanBasePackages} attribute ensures that all components located under {@code
 * com.sikaseal.bank.kata} (in any Maven module) are detected and registered as Spring beans.
 */
@SpringBootApplication(scanBasePackages = "com.sikaseal.bank.kata")
@EnableJpaRepositories(basePackages = "com.sikaseal.bank.kata.infrastructure")
@Import(LauncherApplication.JpaInfraConfig.class)
public class LauncherApplication {

  public static void main(String[] args) {
    SpringApplication.run(LauncherApplication.class, args);
  }

  @Configuration
  static class JpaInfraConfig {

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
      LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
      emf.setDataSource(dataSource);
      emf.setPackagesToScan("com.sikaseal.bank.kata.infrastructure");
      emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      return emf;
    }
  }
}
