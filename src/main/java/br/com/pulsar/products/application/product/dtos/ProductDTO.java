package br.com.pulsar.products.application.product.dtos;

import java.util.UUID;

public record ProductDTO(
        UUID id,
        String name) {
}
