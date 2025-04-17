package br.com.pulsar.products.domain.dtos.http;

import java.util.List;

public record ResponseWrapperBatchDTO(
        List<ResponseBatchDTO> batches
) {
}
