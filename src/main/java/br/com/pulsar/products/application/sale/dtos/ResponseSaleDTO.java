package br.com.pulsar.products.application.sale.dtos;

import br.com.pulsar.products.application.store.dtos.StoreDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ResponseSaleDTO(
        UUID id,
        @Schema(example = "Employee - Test")String employeeId,
        @Schema(example = "1")BigDecimal total,
        LocalDateTime timeStamp,
        StoreDTO store,
        List<SaleDetailDTO> saleDetails,
        List<PaymentDTO> payments) {
}
