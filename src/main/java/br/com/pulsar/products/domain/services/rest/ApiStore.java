package br.com.pulsar.products.domain.services.rest;

import br.com.pulsar.products.domain.dtos.http.ResponseWrapperStoreDTO;
import br.com.pulsar.products.domain.dtos.store.StoreWrapperDTO;
import br.com.pulsar.products.domain.dtos.store.UpdateStoreDTO;

import java.util.UUID;

public interface ApiStore {

    ResponseWrapperStoreDTO createStore(StoreWrapperDTO json);
    ResponseWrapperStoreDTO getStoreById(UUID storeId);
    ResponseWrapperStoreDTO listStoresByActivesTrue();
    ResponseWrapperStoreDTO updateStore(UUID storeId, UpdateStoreDTO json);
    void deActivateStore(UUID storeId);
}
