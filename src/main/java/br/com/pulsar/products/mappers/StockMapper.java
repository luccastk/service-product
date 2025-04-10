package br.com.pulsar.products.mappers;

import br.com.pulsar.products.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.dtos.stock.StockDTO;
import br.com.pulsar.products.models.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {BatchMapper.class})
public interface StockMapper {

    StockDTO toDTO(Stock entity);

    @Mapping(target = "pk", ignore = true)
    Stock toEntity(CreateStockDTO dto);
}
