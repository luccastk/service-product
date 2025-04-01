package br.com.pulsar.products.application.batch.dtos;

import br.com.pulsar.products.domain.valueobjects.BatchId;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

    public record ResponseBatchDTO(
            BatchId id,
            @Schema(example = "10") Integer quantity,
            @Schema(example = "2027-03-21")LocalDate validity,
            LocalDateTime createTime
    ) {
}
