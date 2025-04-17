package br.com.pulsar.products.domain.dtos.kafka;

public record ProductCreateEvent(
        String request_id,
        String status
) {
}
