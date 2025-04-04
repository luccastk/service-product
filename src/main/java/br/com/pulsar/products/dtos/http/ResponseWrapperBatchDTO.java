package br.com.pulsar.products.dtos.http;

import java.util.List;

public record ResponseWrapperBatchDTO(
        List<ResponseBatchDTO> batches
) {
}
