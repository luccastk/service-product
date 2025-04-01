package br.com.pulsar.products.application.product.dtos;

import br.com.pulsar.products.application.batch.dtos.CreateBatchDTO;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateProductListDTO(
        @NotBlank(message = "{PROD-001}")
        String name,
        @Positive(message = "{PROD-007}")
        BigDecimal price,
        @NotNull
        CreateBatchDTO batches) {
}
