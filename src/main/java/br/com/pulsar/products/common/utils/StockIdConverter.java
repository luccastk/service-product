package br.com.pulsar.products.common.utils;

import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.StockId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Converter(autoApply = true)
public class StockIdConverter implements AttributeConverter<StockId, UUID> {
    @Override
    public UUID convertToDatabaseColumn(StockId attribute) {
        return (attribute == null) ? null : attribute.id();
    }

    @Override
    public StockId convertToEntityAttribute(UUID dbData) {
        return (dbData == null) ? null : new StockId(dbData);
    }
}
