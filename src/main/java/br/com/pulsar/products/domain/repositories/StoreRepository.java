package br.com.pulsar.products.domain.repositories;

import br.com.pulsar.products.domain.entities.Store;
import br.com.pulsar.products.domain.valueobjects.StoreId;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    Store save(Store store);
    Optional<Store> findById(StoreId storeId);
    List<Store> findAll();
    boolean existsByNameIgnoringCase(String storeName);
    List<Store> findAllByActiveTrue();
}
