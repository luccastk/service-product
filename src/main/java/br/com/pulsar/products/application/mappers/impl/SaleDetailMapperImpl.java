package br.com.pulsar.products.application.mappers.impl;

import br.com.pulsar.products.application.sale.dtos.CreateSaleDetailDTO;
import br.com.pulsar.products.application.sale.dtos.SaleDetailDTO;
import br.com.pulsar.products.application.mappers.SaleDetailMapper;
import br.com.pulsar.products.infra.persistence.entities.BatchEntity;
import br.com.pulsar.products.infra.persistence.entities.ProductEntity;
import br.com.pulsar.products.infra.sale.Sale;
import br.com.pulsar.products.infra.saledetail.SaleDetail;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class SaleDetailMapperImpl implements SaleDetailMapper {

    @Override
    public SaleDetail toEntity(CreateSaleDetailDTO dto, ProductEntity product, BatchEntity batch, Sale sale) {

        SaleDetail saleDetail = new SaleDetail();
        saleDetail.setQuantity(dto.quantity());
        saleDetail.setProduct(product);
        saleDetail.setSale(sale);

        return saleDetail;
    }

    @Override
    public SaleDetailDTO toDto(SaleDetail entity) {
        return new SaleDetailDTO(
                entity.getId(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getTotal());
    }

    @Override
    public List<SaleDetailDTO> toDTO(List<SaleDetail> entity) {

        List<SaleDetailDTO> list = new ArrayList<>( entity.size() );

        for ( SaleDetail saleDetail : entity) {
            list.add(toDto(saleDetail));
        }

        return list;
    }
}
