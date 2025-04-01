package br.com.pulsar.products.application.sale.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record SaleDetailDTO(
        UUID id,
        @Schema(example = "1")Integer quantity,
        @Schema(example = "199.00")BigDecimal unitPrice,
        @Schema(example = "199.00")BigDecimal total) {
}
