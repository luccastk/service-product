package br.com.pulsar.products.dtos.kafka;

public record ProductCreateEvent(
        String request_id,
        String status
) {
}
