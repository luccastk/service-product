package br.com.pulsar.products.application.store;

import br.com.pulsar.products.application.store.dtos.CreateStoreDTO;
import br.com.pulsar.products.application.store.dtos.ResponseStoreDTO;
import br.com.pulsar.products.application.store.dtos.UpdateStoreDTO;
import br.com.pulsar.products.domain.entities.Store;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import br.com.pulsar.products.infra.persistence.entities.StoreEntity;

import java.util.List;
import java.util.UUID;

public interface StoreService {
   ResponseStoreDTO createStore(CreateStoreDTO json);
   ResponseStoreDTO updateStoreName(StoreId storeId, UpdateStoreDTO json);
   ResponseStoreDTO activateStore(StoreId storeId);
   ResponseStoreDTO detailStore(StoreId storeId);
   List<ResponseStoreDTO> findAllByActiveTrue();
   void deactivateStore(StoreId storeId);
   void addProductToStore(StoreId storeId, ProductId productId);
}
