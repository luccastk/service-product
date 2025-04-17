package br.com.pulsar.products.domain.dtos.store;

import jakarta.validation.constraints.NotNull;

public record StoreWrapperDTO(
        @NotNull(message = "STORE-003")
        CreateStoreDTO store
) {
}
