package br.com.pulsar.products.infra.persistence.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class ProductIdEmbeddable {

    private UUID id;

    public ProductIdEmbeddable() {}

    public ProductIdEmbeddable(UUID id) {
        this.id = id;
    }
}
