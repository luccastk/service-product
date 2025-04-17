package br.com.pulsar.products.dtos.batch;

import java.time.LocalDate;

public record UpdateBatchDTO(
        Integer quantity,
        LocalDate validity) {
}
