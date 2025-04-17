package br.com.pulsar.products.factory;

import br.com.pulsar.products.dtos.http.ResponseStoreDTO;
import br.com.pulsar.products.models.Store;

import java.util.UUID;

public class TestStore {

    private static final String NAME_FOR_STORE = "store test";

    public static Store createValidStore() {
        Store s = new Store();
        s.setId(UUID.randomUUID());
        s.setName(NAME_FOR_STORE);
        return s;
    }

    public static ResponseStoreDTO responseStoreDTO() {
        return new ResponseStoreDTO(
                UUID.randomUUID(),
                createValidStore().getName(),
                true
        );
    }
}
