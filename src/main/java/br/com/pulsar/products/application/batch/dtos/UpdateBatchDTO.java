package br.com.pulsar.products.application.batch.dtos;

import java.time.LocalDate;

public record UpdateBatchDTO(
        Integer quantity,
        LocalDate validity
) {
}
