package br.com.pulsar.products.application.mappers;

import br.com.pulsar.products.application.sale.dtos.CreateSaleDetailDTO;
import br.com.pulsar.products.application.sale.dtos.SaleDetailDTO;
import br.com.pulsar.products.infra.persistence.entities.BatchEntity;
import br.com.pulsar.products.infra.persistence.entities.ProductEntity;
import br.com.pulsar.products.infra.sale.Sale;
import br.com.pulsar.products.infra.saledetail.SaleDetail;

import java.util.List;

public interface SaleDetailMapper {

    SaleDetail toEntity(CreateSaleDetailDTO dto, ProductEntity product, BatchEntity batch, Sale sale);

    SaleDetailDTO toDto(SaleDetail saleDetail);

    List<SaleDetailDTO> toDTO(List<SaleDetail> entity);
}
