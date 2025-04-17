package br.com.pulsar.products.domain.dtos.stock;

import java.math.BigDecimal;

public record UpdateStockDTO(
        BigDecimal price
) {
}
