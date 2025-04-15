package br.com.pulsar.products.dtos.products;

import br.com.pulsar.products.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.dtos.stock.CreateStockDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateProductDTO(
        @NotBlank(message = "{PROD-001}")
        String name,
        @Valid
        @NotNull(message = "{STOCK-004}")
        CreateStockDTO stock,
        @NotNull(message = "{BATCH-011}")
        List<@Valid CreateBatchDTO> batch) {
}
