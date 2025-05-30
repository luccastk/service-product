    package br.com.pulsar.products.domain.dtos.http;

    import io.swagger.v3.oas.annotations.media.Schema;

    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.UUID;

        public record ResponseBatchDTO(
                UUID batchId,
                UUID productId,
                @Schema(example = "Whey Dux Concentrado") String name,
                @Schema(example = "10") Integer quantity,
                @Schema(example = "2027-03-21")LocalDate validity,
                LocalDateTime createTime
        ) {
    }
