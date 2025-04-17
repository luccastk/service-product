package br.com.pulsar.products.services.store.impl;

import br.com.pulsar.products.dtos.store.CreateStoreDTO;
import br.com.pulsar.products.dtos.store.UpdateStoreDTO;
import br.com.pulsar.products.exceptions.DuplicationException;
import br.com.pulsar.products.mappers.StockMapper;
import br.com.pulsar.products.mappers.StoreMapper;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Store;
import br.com.pulsar.products.repositories.StoreRepository;
import br.com.pulsar.products.services.find.FindService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceImplTest {

    @Captor
    private ArgumentCaptor<Store> storeArgumentCaptor;

    @Mock
    private FindService findService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private StoreMapper storeMapper;

    @InjectMocks
    private StoreServiceImpl storeService;

    private UUID storeId;
    private Store store;
    private CreateStoreDTO createStoreDTO;
    private UpdateStoreDTO updateStoreDTO;

    @BeforeEach
    void setUp() {
        storeId = UUID.randomUUID();

        createStoreDTO = new CreateStoreDTO(
                "Store test"
        );

        store = new Store();
        store.setId(storeId);
        store.setName(createStoreDTO.name());
        store.setActive(true);

        updateStoreDTO = new UpdateStoreDTO(
                "New store name",
                true
        );
    }

    @Test
    void shouldCreateStore() {
        given(storeRepository.existsByNameIgnoringCase(createStoreDTO.name())).willReturn(false);
        given(storeMapper.ToEntity(createStoreDTO)).willReturn(store);
        given(storeRepository.save(store)).willReturn(store);

        Store result = storeService.createStore(createStoreDTO);

        then(storeRepository).should().save(storeArgumentCaptor.capture());
        Store storeSaved = storeArgumentCaptor.getValue();

        assertNotNull(result);
        assertEquals(storeSaved.getName(), createStoreDTO.name());
    }

    @Test
    void shouldReturnErrorFromCreateStoreName() {
        given(storeMapper.ToEntity(createStoreDTO)).willReturn(store);
        given(storeRepository.existsByNameIgnoringCase(createStoreDTO.name())).willReturn(true);

        assertThrows(DuplicationException.class, () -> storeService.createStore(createStoreDTO));
    }

    @Test
    void shouldListActivesStores() {
        given(storeRepository.findAllByActiveTrue()).willReturn(List.of(store));

        List<Store> stores = storeService.activeStore();

        assertNotNull(stores);
    }

    @Test
    void shouldUpdateStore() {
        store.setActive(false);

        given(findService.findStoreId(storeId)).willReturn(store);
        given(storeRepository.save(store)).willReturn(store);

        Store result = storeService.updateStore(storeId, updateStoreDTO);

        assertEquals(updateStoreDTO.name(), result.getName());
        assertTrue(result.getActive());

        verify(storeRepository).save(store);
    }

    @Test
    void shouldFindStore() {
        given(findService.findStoreId(storeId)).willReturn(store);

        Store result = storeService.findStore(storeId);

        assertNotNull(result);
        assertEquals(storeId, result.getId());
    }

    @Test
    void shouldNotFoundStore() {
        given(findService.findStoreId(storeId)).willThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> storeService.findStore(storeId));
    }

    @Test
    void shouldDeactivateStore() {
        given(findService.findStoreId(storeId)).willReturn(store);
        given(storeRepository.save(store)).willReturn(store);

        storeService.deActiveStore(storeId);

        assertFalse(store.getActive());

        verify(storeRepository).save(store);
    }
}