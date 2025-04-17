package br.com.pulsar.products.domain.dtos.products;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record ProductWrapperDTO(
        @NotNull(message = "PROD-010")
        List<@Valid CreateProductDTO> products) {
}
