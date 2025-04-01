package br.com.pulsar.products.application.stock.dtos;

import java.math.BigDecimal;

public record UpdateStockPriceDTO(
        BigDecimal price
) {
}
