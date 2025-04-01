package br.com.pulsar.products.application.sale.dtos;

import java.util.List;

public record RequestSaleDTO(
        List<CreatePaymentDTO> payments,
        List<CreateSaleDetailDTO> salesDetail) {
}
