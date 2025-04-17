package br.com.pulsar.products.services.rest.impl;

import br.com.pulsar.products.domain.dtos.http.ResponseStoreDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseWrapperStoreDTO;
import br.com.pulsar.products.domain.dtos.store.CreateStoreDTO;
import br.com.pulsar.products.domain.dtos.store.StoreWrapperDTO;
import br.com.pulsar.products.domain.dtos.store.UpdateStoreDTO;
import br.com.pulsar.products.domain.services.rest.impl.ApiStoreImpl;
import br.com.pulsar.products.factory.TestStore;
import br.com.pulsar.products.domain.mappers.StoreMapper;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.services.store.StoreService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ApiStoreImplTest {

    @InjectMocks
    private ApiStoreImpl restStore;

    @Mock
    private StoreMapper storeMapper;

    @Mock
    private StoreService storeService;

    private Store store;
    private CreateStoreDTO createStoreDTO;
    private ResponseStoreDTO responseStoreDTO;
    private StoreWrapperDTO storeWrapperDTO;
    private UpdateStoreDTO updateStoreDTO;
    private ResponseStoreDTO updateResponseStoreDTO;

    @BeforeEach
    void setUp() {
        store = TestStore.createStore();
        createStoreDTO = TestStore.createStoreDTO();

        responseStoreDTO = new ResponseStoreDTO(
                store.getId(),
                store.getName(),
                store.getActive()
        );

        storeWrapperDTO = TestStore.storeWrapperDTO();

        updateStoreDTO = TestStore.updateStoreDTO();

        updateResponseStoreDTO = new ResponseStoreDTO(
                store.getId(),
                updateStoreDTO.name(),
                updateStoreDTO.active()
        );
    }

    @Test
    void shouldCreateStore() {
        given(storeService.createStore(createStoreDTO)).willReturn(store);
        given(storeMapper.ToDTO(store)).willReturn(responseStoreDTO);

        ResponseWrapperStoreDTO result = restStore.createStore(storeWrapperDTO);

        assertNotNull(result);
        assertEquals(result.stores().get(0).name(), store.getName());
    }

    @Test
    void shouldGetStoreById() {
        given(storeService.findStore(store.getId())).willReturn(store);
        given(storeMapper.ToDTO(store)).willReturn(responseStoreDTO);

        ResponseWrapperStoreDTO result = restStore.getStoreById(store.getId());

        assertNotNull(result);
        assertEquals(result.stores().get(0).id(), store.getId());
    }

    @Test
    void shouldListStoresByActivesTrue() {
        given(storeService.activeStore()).willReturn(List.of(store));
        given(storeMapper.ToDTO(List.of(store))).willReturn(List.of(responseStoreDTO));

        ResponseWrapperStoreDTO result = restStore.listStoresByActivesTrue();

        assertNotNull(result);
        assertTrue(result.stores().get(0).active());
    }

    @Test
    void shouldListStoreButNotReturnStoreFalse() {

        given(storeService.activeStore()).willReturn(List.of());
        given(storeMapper.ToDTO(List.of())).willReturn(List.of());

        ResponseWrapperStoreDTO result = restStore.listStoresByActivesTrue();

        assertTrue(result.stores().isEmpty());
    }

    @Test
    void updateStore() {
        store.setName(updateStoreDTO.name());

        given(storeService.updateStore(store.getId(), updateStoreDTO)).willReturn(store);
        given(storeMapper.ToDTO(store)).willReturn(updateResponseStoreDTO);

        ResponseWrapperStoreDTO result = restStore.updateStore(store.getId(), updateStoreDTO);

        assertNotNull(result);
        assertEquals(result.stores().get(0).name(), updateStoreDTO.name());
    }

    @Test
    void shouldThrowWhenStoreNotFound() {
        given(storeService.findStore(store.getId()))
                .willThrow(new EntityNotFoundException("Loja não encontrada"));
        assertThrows(EntityNotFoundException.class,
                () -> restStore.getStoreById(store.getId()));
        verify(storeMapper, never()).ToDTO(any(Store.class));
    }

    @Test
    void deActivateStore() {
        restStore.deActivateStore(store.getId());
        verify(storeService).deActiveStore(store.getId());
    }
}