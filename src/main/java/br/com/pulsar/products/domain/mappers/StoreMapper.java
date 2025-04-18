package br.com.pulsar.products.domain.mappers;

import br.com.pulsar.products.domain.dtos.http.ResponseStoreDTO;
import br.com.pulsar.products.domain.dtos.store.CreateStoreDTO;
import br.com.pulsar.products.domain.models.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring", uses = {StockMapper.class})
public interface StoreMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "pk", ignore = true)
    Store ToEntity(CreateStoreDTO dto);

    ResponseStoreDTO ToDTO(Store entity);

    List<ResponseStoreDTO> ToDTO(List<Store> entity);
}
