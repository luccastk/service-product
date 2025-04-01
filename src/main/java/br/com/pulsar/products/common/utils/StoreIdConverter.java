package br.com.pulsar.products.common.utils;

import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Converter(autoApply = true)
public class StoreIdConverter implements AttributeConverter<StoreId, UUID> {

    @Override
    public UUID convertToDatabaseColumn(StoreId attribute) {
        return (attribute == null) ? null : attribute.id();
    }

    @Override
    public StoreId convertToEntityAttribute(UUID dbData) {
        return (dbData == null) ? null : new StoreId(dbData);
    }
}
