package br.com.pulsar.products.services.store;

import br.com.pulsar.products.dtos.store.CreateStoreDTO;
import br.com.pulsar.products.dtos.store.UpdateStoreDTO;
import br.com.pulsar.products.models.Store;

import java.util.List;
import java.util.UUID;

public interface StoreService {

   Store createStore(CreateStoreDTO dto);

   List<Store> activeStore();

   Store updateStore(UUID storeId, UpdateStoreDTO dto);

   Store findStore(UUID storeId);

   void deActiveStore(UUID storeId);
}
