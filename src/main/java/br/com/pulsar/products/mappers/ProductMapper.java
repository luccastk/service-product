package br.com.pulsar.products.mappers;

import br.com.pulsar.products.dtos.http.ResponseProductDTO;
import br.com.pulsar.products.dtos.products.CreateProductDTO;
import br.com.pulsar.products.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {StockMapper.class})
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "pk", ignore = true)
    Product ToEntity(CreateProductDTO dto);

    List<Product> ToEntity(List<CreateProductDTO> dto);

    @Mapping(target = "stocks", source = "stock")
    ResponseProductDTO ToDTO(Product entity);

    List<ResponseProductDTO> ToDTO(List<Product> entity);
}
