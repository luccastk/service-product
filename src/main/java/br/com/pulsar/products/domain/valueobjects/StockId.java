package br.com.pulsar.products.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record StockId(UUID id) {
    public StockId{
        Assert.notNull(id, "ID must not be null.");
    }

    public StockId(){
        this(UUID.randomUUID());
    }
}
