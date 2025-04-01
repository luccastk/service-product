package br.com.pulsar.products.domain.valueobjects;

import org.springframework.util.Assert;

import java.util.UUID;

public record BatchId(UUID id) {

    public BatchId{
        Assert.notNull(id, "ID must not be null.");
    }

    public BatchId(){
        this(UUID.randomUUID());
    }
}
