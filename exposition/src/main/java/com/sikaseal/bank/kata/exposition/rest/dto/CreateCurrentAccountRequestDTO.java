package com.sikaseal.bank.kata.exposition.rest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Data Transfer Object that captures the JSON payload used to create a current account.
 *
 * <p>Overdraft semantics:
 *
 * <ul>
 *   <li>If {@code authorizedOverdraft} is {@code null}, the created current account will have no
 *       authorized overdraft (equivalent to an overdraft limit of zero).
 *   <li>If {@code authorizedOverdraft} is provided, it must be greater than or equal to zero and
 *       represents the maximum authorized overdraft for the current account.
 * </ul>
 */
public record CreateCurrentAccountRequestDTO(
    @NotBlank(message = "L'identifiant du compte est obligatoire") String accountId,
    @NotNull(message = "Le solde initial est obligatoire")
        @DecimalMin(value = "0.0", message = "Le solde initial ne doit pas être négatif")
        BigDecimal initialBalance,
    @DecimalMin(value = "0.0", message = "Le découvert autorisé doit être positif ou nul")
        BigDecimal authorizedOverdraft) {}
