package br.com.pulsar.products.application.sale.dtos;

import br.com.pulsar.products.infra.payment.PaymentMethod;
import br.com.pulsar.products.infra.payment.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentDTO(
        UUID id,
        @Schema(example = "199.00")BigDecimal amount,
        LocalDateTime createTime,
        @Schema(example = "PAGO")PaymentStatus status,
        @Schema(example = "PIX")PaymentMethod paymentMethod) {
}
