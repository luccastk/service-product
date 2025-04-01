package br.com.pulsar.products.infra.persistence.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class StoreIdEmbeddable {

    private UUID id;

    public StoreIdEmbeddable() {}

    public StoreIdEmbeddable(UUID id) {
        this.id = id;
    }
}
