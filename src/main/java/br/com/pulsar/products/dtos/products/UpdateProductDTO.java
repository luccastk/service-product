package br.com.pulsar.products.dtos.products;

public record UpdateProductDTO(
        String name,
        Boolean active) {
}
