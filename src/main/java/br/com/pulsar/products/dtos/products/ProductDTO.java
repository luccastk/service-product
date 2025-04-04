package br.com.pulsar.products.dtos.products;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ProductDTO(
        UUID id,
        String name) {
}
