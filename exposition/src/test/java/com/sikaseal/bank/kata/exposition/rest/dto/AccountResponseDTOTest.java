package com.sikaseal.bank.kata.exposition.rest.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class AccountResponseDTOTest {

  @Test
  void should_expose_all_fields_when_record_is_created() {
    AccountResponseDTO dto =
        new AccountResponseDTO(
            "ACC-1", new BigDecimal("100.00"), "CURRENT", new BigDecimal("0.00"));

    assertThat(dto.accountId()).isEqualTo("ACC-1");
    assertThat(dto.balance()).isEqualByComparingTo(new BigDecimal("100.00"));
    assertThat(dto.accountType()).isEqualTo("CURRENT");
    assertThat(dto.authorizedOverdraft()).isEqualByComparingTo(new BigDecimal("0.00"));
  }
}
