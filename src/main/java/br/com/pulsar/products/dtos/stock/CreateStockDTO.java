package br.com.pulsar.products.dtos.stock;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateStockDTO(
        @Positive(message = "{PROD-007}")
        BigDecimal price
) {
}
