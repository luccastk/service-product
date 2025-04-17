package br.com.pulsar.products.factory;

import br.com.pulsar.products.domain.dtos.http.ResponseStoreDTO;
import br.com.pulsar.products.domain.dtos.store.CreateStoreDTO;
import br.com.pulsar.products.domain.dtos.store.StoreWrapperDTO;
import br.com.pulsar.products.domain.dtos.store.UpdateStoreDTO;
import br.com.pulsar.products.domain.models.Store;

import java.util.UUID;

public class TestStore {

    private static final String NAME_FOR_STORE = "store test";
    private static final String NEW_NAME_FOR_STORE = "new store test";

    public static Store createStore() {
        Store s = new Store();
        s.setPk(1L);
        s.setId(UUID.randomUUID());
        s.setName(NAME_FOR_STORE);
        s.setActive(true);
        return s;
    }

    public static ResponseStoreDTO responseStoreDTO() {
        return new ResponseStoreDTO(
                UUID.randomUUID(),
                createStore().getName(),
                true
        );
    }

    public static CreateStoreDTO createStoreDTO() {
        return new CreateStoreDTO(
                NAME_FOR_STORE
        );
    }

    public static StoreWrapperDTO storeWrapperDTO() {
        return new StoreWrapperDTO(
                createStoreDTO()
        );
    }

    public static UpdateStoreDTO updateStoreDTO() {
        return new UpdateStoreDTO(
                NEW_NAME_FOR_STORE,
                true
        );
    }
}
