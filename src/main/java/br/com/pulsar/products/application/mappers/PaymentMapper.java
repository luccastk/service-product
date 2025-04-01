package br.com.pulsar.products.application.mappers;

import br.com.pulsar.products.application.sale.dtos.CreatePaymentDTO;
import br.com.pulsar.products.application.sale.dtos.PaymentDTO;
import br.com.pulsar.products.infra.payment.Payment;
import br.com.pulsar.products.infra.sale.Sale;

import java.util.List;

public interface PaymentMapper {

    Payment toEntity(CreatePaymentDTO dto, Sale sale);

    PaymentDTO toDto(Payment entity);

    List<PaymentDTO> toDto(List<Payment> entity);
}
