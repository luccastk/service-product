package br.com.pulsar.products.application.mappers;

import br.com.pulsar.products.application.sale.dtos.ResponseSaleDTO;
import br.com.pulsar.products.infra.payment.Payment;
import br.com.pulsar.products.infra.sale.Sale;
import br.com.pulsar.products.infra.saledetail.SaleDetail;
import br.com.pulsar.products.infra.persistence.entities.StoreEntity;

import java.math.BigDecimal;
import java.util.List;

public interface SaleMapper {

    Sale toEntity(StoreEntity store, BigDecimal total, List<Payment> payments, List<SaleDetail> saleDetails);

    ResponseSaleDTO toResponseDTO(Sale entity);

    List<ResponseSaleDTO> toResponseDTO(List<Sale> entity);
}
