package br.com.pulsar.products.dtos.kafka;

import java.util.UUID;

public record FileUploadEvent(
        String request_id,
        UUID store_id
) {
}
