package br.com.pulsar.products.application.product.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record BulkCreateProductDTO(
        @NotNull(message = "PROD-010")
        List<@Valid CreateProductListDTO> products) {
}
