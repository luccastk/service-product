package br.com.pulsar.products.common.utils;

import br.com.pulsar.products.domain.valueobjects.StoreId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToStoreIdConverter implements Converter<String, StoreId> {
    @Override
    public StoreId convert(String source) {
        try {
            UUID uuid = UUID.fromString(source);
            return new StoreId(uuid);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid StoreId format: " + source, ex);
        }
    }
}