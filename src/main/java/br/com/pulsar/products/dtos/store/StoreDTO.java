package br.com.pulsar.products.dtos.store;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record StoreDTO(
        UUID id,
        String name) {
}
