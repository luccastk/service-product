package br.com.pulsar.products.domain.mappers;

import br.com.pulsar.products.domain.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseBatchDTO;
import br.com.pulsar.products.domain.models.Batch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = { ProductMapper.class, StoreMapper.class})
public interface BatchMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pk", ignore = true)
    Batch toEntity(CreateBatchDTO dto);

    List<Batch> toEntity(List<CreateBatchDTO> dto);

    @Mapping(target = "batchId", source = "id")
    @Mapping(target = "productId", source = "stock.product.id")
    @Mapping(target = "name", source = "stock.product.name")
    ResponseBatchDTO toDTOResponse(Batch entity);

    List<ResponseBatchDTO> toDTOResponse(List<Batch> entity);
}
