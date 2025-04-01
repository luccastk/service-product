package br.com.pulsar.products.application.sale.dtos;

import br.com.pulsar.products.infra.payment.PaymentMethod;
import br.com.pulsar.products.infra.payment.PaymentStatus;

import java.math.BigDecimal;

public record CreatePaymentDTO(
        PaymentMethod paymentMethod,
        BigDecimal amount,
        PaymentStatus status) {
}
