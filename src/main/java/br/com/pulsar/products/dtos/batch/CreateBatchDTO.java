package br.com.pulsar.products.dtos.batch;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateBatchDTO(
        @NotNull(message = "{BATCH-007}")
        @PositiveOrZero(message = "{BATCH-008}")
        Integer quantity,
        @FutureOrPresent(message = "{PROD-005}")
        LocalDate validity
) {
}
