package br.com.pulsar.products.application.batch.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record CreateBatchDTO(
        @NotNull(message = "{BATCH-007}")
        @PositiveOrZero(message = "{BATCH-008}")
        Integer quantity,
        @FutureOrPresent(message = "{PROD-005}")
        LocalDate validity
) {
}
