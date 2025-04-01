package br.com.pulsar.products.application.mappers.impl;

import br.com.pulsar.products.application.sale.dtos.CreatePaymentDTO;
import br.com.pulsar.products.application.sale.dtos.PaymentDTO;
import br.com.pulsar.products.application.mappers.PaymentMapper;
import br.com.pulsar.products.infra.payment.Payment;
import br.com.pulsar.products.infra.sale.Sale;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment toEntity(CreatePaymentDTO dto, Sale sale) {
        Payment payment =  new Payment();

        payment.setPaymentMethod(dto.paymentMethod());
        payment.setStatus(dto.status());
        payment.setAmount(dto.amount());
        payment.setSale(sale);

        return payment;
    }

    @Override
    public PaymentDTO toDto(Payment entity) {
        return new PaymentDTO(
                entity.getId(),
                entity.getAmount(),
                entity.getDatePayment(),
                entity.getStatus(),
                entity.getPaymentMethod()
        );
    }

    @Override
    public List<PaymentDTO> toDto(List<Payment> entity) {

        List<PaymentDTO> list = new ArrayList<>(entity.size());

        for (Payment payment : entity) {
            list.add(toDto(payment));
        }

        return list;
    }
}
