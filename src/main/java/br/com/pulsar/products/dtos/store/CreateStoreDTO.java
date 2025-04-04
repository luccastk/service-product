package br.com.pulsar.products.dtos.store;

import jakarta.validation.constraints.NotBlank;

public record CreateStoreDTO(
        @NotBlank(message = "{STORE-003}")
        String name) {
}
