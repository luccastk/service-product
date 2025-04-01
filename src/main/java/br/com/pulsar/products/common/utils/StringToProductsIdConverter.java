package br.com.pulsar.products.common.utils;

import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToProductsIdConverter implements Converter<String, ProductId> {
    @Override
    public ProductId convert(String source) {
        try {
            UUID uuid = UUID.fromString(source);
            return new ProductId(uuid);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid StoreId format: " + source, ex);
        }
    }
}