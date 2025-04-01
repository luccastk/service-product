package br.com.pulsar.products.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record ProductId(UUID id) {
    public ProductId{
        Assert.notNull(id, "ID must not be null.");
    }

    public ProductId(){
        this(UUID.randomUUID());
    }
}
