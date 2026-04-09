package com.sikaseal.bank.kata.exposition.rest.dto;

import java.math.BigDecimal;

public record AccountResponseDTO(
        String accountId,
        BigDecimal balance,
        String accountType,
        BigDecimal authorizedOverdraft
) {}
