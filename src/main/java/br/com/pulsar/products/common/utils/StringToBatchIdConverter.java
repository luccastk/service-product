package br.com.pulsar.products.common.utils;

import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringToBatchIdConverter implements Converter<String, BatchId> {
    @Override
    public BatchId convert(String source) {
        try {
            UUID uuid = UUID.fromString(source);
            return new BatchId(uuid);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid StoreId format: " + source, ex);
        }
    }
}