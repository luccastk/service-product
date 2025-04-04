package br.com.pulsar.products.dtos.products;

import br.com.pulsar.products.models.Product;

public record UpdateProductDTO(
        String name,
        Boolean active) {
}
