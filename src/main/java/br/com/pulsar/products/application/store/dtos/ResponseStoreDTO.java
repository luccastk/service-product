package br.com.pulsar.products.application.store.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ResponseStoreDTO(
        UUID id,
        @Schema(example = "Store Test")String name,
        @Schema(example = "true")Boolean active) {
}
