package br.com.pulsar.products.application.mappers;

import br.com.pulsar.products.application.store.dtos.ResponseStoreDTO;
import br.com.pulsar.products.domain.entities.Store;
import br.com.pulsar.products.infra.persistence.entities.StoreEntity;

import java.util.List;

public interface StoreMapper {

    StoreEntity toEntity(Store entityDomain);

    ResponseStoreDTO toDTO(Store entityDomain);

    List<StoreEntity> toEntity(List<Store> entityDomain);

    List<ResponseStoreDTO> toDTO(List<Store> entityDomain);

    Store toDomain(StoreEntity entity);

    List<Store> toDomain(List<StoreEntity> entity);
}
