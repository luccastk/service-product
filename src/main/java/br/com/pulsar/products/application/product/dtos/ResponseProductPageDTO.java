package br.com.pulsar.products.application.product.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ResponseProductPageDTO(List<ResponseProductDTO> productList,
                                     @Schema(example = "1") String nextCursor) {
}
