package br.com.pulsar.products.application.store.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateStoreDTO(
        @NotBlank(message = "{STORE-003}")
        String name) {
}
