package br.com.pulsar.products.application.store.impl;

import br.com.pulsar.products.application.FindService;
import br.com.pulsar.products.application.mappers.StoreMapper;
import br.com.pulsar.products.application.store.StoreService;
import br.com.pulsar.products.application.store.dtos.CreateStoreDTO;
import br.com.pulsar.products.application.store.dtos.ResponseStoreDTO;
import br.com.pulsar.products.application.store.dtos.UpdateStoreDTO;
import br.com.pulsar.products.domain.entities.Store;
import br.com.pulsar.products.domain.repositories.StoreRepository;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreMapper storeMapper;
    private final StoreRepository storeRepository;
    private final FindService findService;

    @Transactional
    @Override
    public ResponseStoreDTO createStore(CreateStoreDTO json) {
        Store store = new Store(json.name());
        return storeMapper.toDTO(storeRepository.save(store));
    }

    @Override
    public ResponseStoreDTO updateStoreName(StoreId storeId, UpdateStoreDTO json) {
        Store store = findService.getStoreId(storeId);
        store.rename(json.name());
        return storeMapper.toDTO(storeRepository.save(store));
    }

    @Override
    public ResponseStoreDTO activateStore(StoreId storeId) {
        Store store = findService.getStoreId(storeId);
        store.activate();
        return storeMapper.toDTO(storeRepository.save(store));
    }

    @Override
    public ResponseStoreDTO detailStore(StoreId storeId) {
        return storeMapper.toDTO(findService.getStoreId(storeId));
    }

    @Override
    public void deactivateStore(StoreId storeId) {
        Store store = findService.getStoreId(storeId);
        store.deActivate();
        storeRepository.save(store);
    }

    @Override
    public List<ResponseStoreDTO> findAllByActiveTrue() {
        return storeMapper.toDTO(storeRepository.findAllByActiveTrue());
    }

    @Override
    public void addProductToStore(StoreId storeId, ProductId productId) {
        Store store = findService.getStoreId(storeId);
        store.addProduct(productId);
    }
}
