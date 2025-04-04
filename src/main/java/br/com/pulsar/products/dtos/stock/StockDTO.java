package br.com.pulsar.products.dtos.stock;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record StockDTO(
        @Schema(example = "10")Integer quantity,
        @Schema(example = "199.00")BigDecimal price) {
}
