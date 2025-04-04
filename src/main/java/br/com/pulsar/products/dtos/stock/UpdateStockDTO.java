package br.com.pulsar.products.dtos.stock;

import java.math.BigDecimal;

public record UpdateStockDTO(
        BigDecimal price
) {
}
