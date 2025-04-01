package br.com.pulsar.products.application.product.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record ResponseProductDTO(
        UUID id,
        @Schema(example = "Product Test 1") String name,
        @Schema(example = "true")Boolean active,
        @Schema(example = "10")int quantity,
        @Schema(example = "199.00") BigDecimal price) {
}
