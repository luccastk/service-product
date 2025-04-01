package br.com.pulsar.products.application.sale.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record CreateSaleDetailDTO(
        @Positive(message = "{PROD-003}")
        @NotNull(message = "{PROD-002}")
        Integer quantity,
        @NotBlank(message = "{PROD-014}")
        UUID productId,
        @NotBlank(message = "{BATCH-001}")
        UUID batchId) {

}
