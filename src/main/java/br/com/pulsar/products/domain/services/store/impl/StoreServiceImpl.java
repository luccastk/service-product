package br.com.pulsar.products.domain.services.store.impl;

import br.com.pulsar.products.domain.dtos.store.CreateStoreDTO;
import br.com.pulsar.products.domain.dtos.store.UpdateStoreDTO;
import br.com.pulsar.products.exceptions.DuplicationException;
import br.com.pulsar.products.domain.mappers.StoreMapper;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.repositories.StoreRepository;
import br.com.pulsar.products.domain.services.find.FindService;
import br.com.pulsar.products.domain.services.store.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreMapper mapper;
    private final StoreRepository storeRepository;
    private final MessageSource m;
    private final FindService find;

    @Transactional
    @Override
    public Store createStore(CreateStoreDTO json) {
        Store store = mapper.ToEntity(json);
        verifyByName(store);
        return storeRepository.save(store);
    }

    @Transactional
    private void verifyByName(Store store){
        if (storeRepository.existsByNameIgnoringCase(store.getName())) {
            throw new DuplicationException(m.getMessage("STORE-002", null, Locale.getDefault()));
        }
    }

    @Transactional
    @Override
    public List<Store> activeStore() {
        return findActives();
    }

    @Transactional
    private List<Store> findActives(){
        return storeRepository.findAllByActiveTrue();
    }

    @Transactional
    @Override
    public Store updateStore(UUID storeId, UpdateStoreDTO json) {
        return storeRepository.save(
                updateStoreEntity(storeId, json)
        );
    }

    private Store updateStoreEntity(UUID storeId, UpdateStoreDTO json) {
        Store store = find.findStoreId(storeId);
        Optional.ofNullable(json.name()).ifPresent(store::setName);
        Optional.ofNullable(json.active()).ifPresent(store::setActive);
        return store;
    }

    @Override
    public Store findStore(UUID storeId) {
        return find.findStoreId(storeId);
    }

    @Transactional
    @Override
    public void deActiveStore(UUID storeId) {
        Store store = find.findStoreId(storeId);
        store.setActive(false);
        storeRepository.save(store);
    }
}
