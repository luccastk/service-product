package br.com.pulsar.products.infra.persistence.adapters;

import br.com.pulsar.products.application.mappers.StoreMapper;
import br.com.pulsar.products.domain.entities.Store;
import br.com.pulsar.products.domain.repositories.StoreRepository;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import br.com.pulsar.products.infra.persistence.entities.StoreEntity;
import br.com.pulsar.products.infra.persistence.repositories.SpringDataStoreRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StoreRepositoryImpl implements StoreRepository{

    private final SpringDataStoreRepository dataStoreRepository;
    private final StoreMapper mapper;

    public StoreRepositoryImpl(SpringDataStoreRepository dataStoreRepository, StoreMapper mapper) {
        this.dataStoreRepository = dataStoreRepository;
        this.mapper = mapper;
    }

    @Override
    public Store save(Store store) {
        StoreEntity entity = mapper.toEntity(store);
        return mapper.toDomain(dataStoreRepository.save(entity));
    }

    @Override
    public Optional<Store> findById(StoreId id) {
        return dataStoreRepository.findByUuid(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Store> findAll() {
        return dataStoreRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByNameIgnoringCase(String storeName) {
        return dataStoreRepository.existsByNameIgnoringCase(storeName);
    }

    @Override
    public List<Store> findAllByActiveTrue() {
        return mapper.toDomain(dataStoreRepository.findAllByActiveTrue());
    }
}
