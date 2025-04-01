package br.com.pulsar.products.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record StoreId(UUID id) {
    public StoreId{
        Assert.notNull(id, "ID must not be null.");
    }

    public StoreId(){
        this(UUID.randomUUID());
    }
}
