package br.com.pulsar.products.dtos.http;

import br.com.pulsar.products.dtos.stock.StockDTO;
import br.com.pulsar.products.models.Store;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

public record ResponseStoreDTO(
        UUID id,
        @Schema(example = "Store Test")String name,
        @Schema(example = "true")Boolean active) {
}
