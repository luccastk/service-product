package br.com.pulsar.products.application.store.dtos;

import br.com.pulsar.products.domain.valueobjects.StoreId;

import java.util.UUID;

public record StoreDTO(
        StoreId id,
        String name) {
}
