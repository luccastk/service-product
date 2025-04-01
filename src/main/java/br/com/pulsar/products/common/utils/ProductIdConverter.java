package br.com.pulsar.products.common.utils;

import br.com.pulsar.products.domain.valueobjects.ProductId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Converter(autoApply = true)
public class ProductIdConverter implements AttributeConverter<ProductId, UUID> {

    @Override
    public UUID convertToDatabaseColumn(ProductId attribute) {
        return (attribute == null) ? null : attribute.id();
    }

    @Override
    public ProductId convertToEntityAttribute(UUID dbData) {
        return (dbData == null) ? null : new ProductId(dbData);
    }
}
