package br.com.pulsar.products.domain.dtos.stock;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record StockDTO(
        @Schema(example = "10")Integer quantity,
        @Schema(example = "199.00")BigDecimal price) {
}
