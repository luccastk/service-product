package br.com.pulsar.products.domain.dtos.batch;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BatchWrapperDTO(
        @NotNull(message = "{BATCH-011}")
        List<@Valid CreateBatchDTO> batches) {
}
