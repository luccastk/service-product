package br.com.pulsar.products.domain.dtos.kafka;

import java.util.UUID;

public record FileUploadEvent(
        String request_id,
        UUID storeId
) {
}
