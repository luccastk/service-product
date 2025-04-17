package br.com.pulsar.products.domain.dtos.products;

public record UpdateProductDTO(
        String name,
        Boolean active) {
}
