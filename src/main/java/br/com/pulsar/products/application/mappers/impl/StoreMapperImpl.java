package br.com.pulsar.products.application.mappers.impl;

import br.com.pulsar.products.application.mappers.StoreMapper;
import br.com.pulsar.products.application.store.dtos.ResponseStoreDTO;
import br.com.pulsar.products.domain.entities.Store;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import br.com.pulsar.products.infra.persistence.entities.StoreEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class StoreMapperImpl implements StoreMapper {

    @Override
    public StoreEntity toEntity(Store entityDomain){
        if ( entityDomain == null) {
            return null;
        }

        StoreEntity storeEntity = new StoreEntity();

        storeEntity.setId(entityDomain.getId());
        storeEntity.setName(entityDomain.getName());
        storeEntity.setActive(entityDomain.isActive());
        storeEntity.setProducts(entityDomain.getProductIds());

        return storeEntity;
    }

    @Override
    public ResponseStoreDTO toDTO(Store entityDomain){
        if ( entityDomain == null) {
            return null;
        }

        UUID id = null;
        String name = null;
        Boolean active = null;

        id = entityDomain.getId().id();
        name = entityDomain.getName();
        active = entityDomain.isActive();

        return new ResponseStoreDTO(
                id,
                name,
                active
        );
    }

    @Override
    public List<StoreEntity> toEntity(List<Store> entityDomain) {
        List<StoreEntity> list = new ArrayList<>( entityDomain.size() );

        for ( Store store : entityDomain) {
            list.add(toEntity(store));
        }

        return list;
    }

    @Override
    public List<ResponseStoreDTO> toDTO(List<Store> entityDomain) {
        List<ResponseStoreDTO> list = new ArrayList<>( entityDomain.size() );

        for ( Store store : entityDomain) {
            list.add(toDTO(store));
        }

        return list;
    }

    @Override
    public Store toDomain(StoreEntity entity) {
        if ( entity == null ) {
            return null;
        }

        return new Store(
                entity.getId(),
                entity.getName(),
                entity.getActive(),
                entity.getProducts()
        );
    }

    @Override
    public List<Store> toDomain(List<StoreEntity> entity) {
        List<Store> list = new ArrayList<>( entity.size() );

        for ( StoreEntity store : entity) {
            list.add(toDomain(store));
        }

        return list;
    }
}
