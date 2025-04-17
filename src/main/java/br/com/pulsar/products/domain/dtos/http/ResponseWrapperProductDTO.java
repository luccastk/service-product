package br.com.pulsar.products.domain.dtos.http;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ResponseWrapperProductDTO(List<ResponseProductDTO> products,
                                        @Schema(example = "1") String nextCursor) {
}
