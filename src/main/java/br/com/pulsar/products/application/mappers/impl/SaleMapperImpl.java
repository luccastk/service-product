package br.com.pulsar.products.application.mappers.impl;

import br.com.pulsar.products.application.sale.dtos.ResponseSaleDTO;
import br.com.pulsar.products.application.store.dtos.StoreDTO;
import br.com.pulsar.products.application.mappers.PaymentMapper;
import br.com.pulsar.products.application.mappers.SaleDetailMapper;
import br.com.pulsar.products.application.mappers.SaleMapper;
import br.com.pulsar.products.infra.payment.Payment;
import br.com.pulsar.products.infra.sale.Sale;
import br.com.pulsar.products.infra.saledetail.SaleDetail;
import br.com.pulsar.products.infra.persistence.entities.StoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class SaleMapperImpl implements SaleMapper {

    @Autowired
    private SaleDetailMapper saleDetailMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public Sale toEntity(StoreEntity store, BigDecimal total, List<Payment> payments, List<SaleDetail> saleDetails) {
        Sale sale = new Sale();

        sale.setSaleDetails(saleDetails);
        sale.setPayment(payments);
        sale.setStore(store);
        sale.setEmployeeId("123448599");
        sale.setTotal(total);

        return sale;
    }

    @Override
    public ResponseSaleDTO toResponseDTO(Sale entity) {
        return new ResponseSaleDTO(
                entity.getId(),
                entity.getEmployeeId(),
                entity.getTotal(),
                entity.getTimeStampSale(),
                new StoreDTO(entity.getStore().getId(), entity.getStore().getName()),
                saleDetailMapper.toDTO(entity.getSaleDetails()),
                paymentMapper.toDto(entity.getPayment())
        );
    }

    @Override
    public List<ResponseSaleDTO> toResponseDTO(List<Sale> entity) {
        List<ResponseSaleDTO> list = new ArrayList<>(entity.size());

        for (Sale sale : entity) {
            list.add( toResponseDTO(sale));
        }

        return list;
    }
}
