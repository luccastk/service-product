package br.com.pulsar.products.common.utils;

import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Converter(autoApply = true)
public class BatchIdConverter implements AttributeConverter<BatchId, UUID> {

    @Override
    public UUID convertToDatabaseColumn(BatchId attribute) {
        return (attribute == null) ? null : attribute.id();
    }

    @Override
    public BatchId convertToEntityAttribute(UUID dbData) {
        return (dbData == null) ? null : new BatchId(dbData);
    }
}
