package br.com.pulsar.products.dtos.http;

import br.com.pulsar.products.dtos.stock.StockDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ResponseProductDTO(
        UUID id,
        @Schema(example = "Product Test 1") String name,
        @Schema(example = "true")Boolean active,
        StockDTO stocks) {
}
