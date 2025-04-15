package br.com.pulsar.products.dtos.store;

import java.util.UUID;

public record StoreDTO(
        UUID id,
        String name) {
}
