package br.com.pulsar.products.application.product.dtos;

public record UpdateProductDTO(
        String name,
        Boolean active) {
}
