package br.com.pulsar.products.dtos.products;

import java.util.UUID;

public record ProductDTO(
        UUID id,
        String name) {
}
