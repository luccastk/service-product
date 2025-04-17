package br.com.pulsar.products.domain.services.rest.impl;

import br.com.pulsar.products.domain.dtos.http.ResponseWrapperStoreDTO;
import br.com.pulsar.products.domain.dtos.store.StoreWrapperDTO;
import br.com.pulsar.products.domain.dtos.store.UpdateStoreDTO;
import br.com.pulsar.products.domain.mappers.StoreMapper;
import br.com.pulsar.products.domain.services.rest.ApiStore;
import br.com.pulsar.products.domain.services.store.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ApiStoreImpl implements ApiStore {

    private final StoreService storeService;
    private final StoreMapper storeMapper;

    public ApiStoreImpl(StoreService storeService, StoreMapper storeMapper) {
        this.storeService = storeService;
        this.storeMapper = storeMapper;
    }

    @Override
    public ResponseWrapperStoreDTO createStore(StoreWrapperDTO json) {
        return new ResponseWrapperStoreDTO(
                List.of(storeMapper.ToDTO(
                        storeService.createStore(json.store())
                )));
    }

    @Override
    public ResponseWrapperStoreDTO getStoreById(UUID storeId) {
        return new ResponseWrapperStoreDTO(
                List.of(storeMapper.ToDTO(
                        storeService.findStore(storeId)
                ))
        );
    }

    @Override
    public ResponseWrapperStoreDTO listStoresByActivesTrue() {
        return new ResponseWrapperStoreDTO(
                storeMapper.ToDTO(
                        storeService.activeStore()
                )
        );
    }

    @Override
    public ResponseWrapperStoreDTO updateStore(UUID storeId, UpdateStoreDTO json) {
        return new ResponseWrapperStoreDTO(
                List.of(storeMapper.ToDTO(
                        storeService.updateStore(storeId, json)
                ))
        );
    }

    @Override
    public void deActivateStore(UUID storeId) {
        storeService.deActiveStore(storeId);
    }
}
