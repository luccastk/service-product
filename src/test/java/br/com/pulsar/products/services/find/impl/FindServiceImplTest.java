package br.com.pulsar.products.services.find.impl;

import br.com.pulsar.products.models.Batch;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Store;
import br.com.pulsar.products.repositories.BatchRepository;
import br.com.pulsar.products.repositories.ProductRepository;
import br.com.pulsar.products.repositories.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.kafka.common.Uuid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class FindServiceImplTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BatchRepository batchRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private FindServiceImpl findService;

    private UUID storeId;
    private UUID productId;
    private UUID batchId;
    private Store store;
    private Product product;
    private Batch batch;

    @BeforeEach
    void setUp() {
        storeId = UUID.randomUUID();
        productId = UUID.randomUUID();

        store = new Store();
        store.setId(storeId);

        product = new Product();
        product.setId(productId);
        product.setStore(store);

        batch = new Batch();
        batch.setId(batchId);
    }

    @Test
    void shouldFindProductByStoreAndId() {
        given(productRepository.findByStore_IdAndId(storeId, productId)).willReturn(Optional.of(product));

        Product result = findService.findProductByStoreAndId(storeId, productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());

        verify(productRepository).findByStore_IdAndId(storeId, productId);
    }

    @Test
    void shouldNotFindProductByStoreAndId() {
        given(productRepository.findByStore_IdAndId(storeId, productId))
                .willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                findService.findProductByStoreAndId(storeId, productId)
        );
    }

    @Test
    void shouldFindStoreId() {
        given(storeRepository.findByUuid(storeId)).willReturn(Optional.of(store));

        Store result = findService.findStoreId(storeId);

        assertNotNull(result);
        assertEquals(storeId, result.getId());

        verify(storeRepository).findByUuid(storeId);
    }

    @Test
    void shouldNotFindStoreId() {
        given(storeRepository.findByUuid(storeId)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> findService.findStoreId(storeId));
    }

    @Test
    void shouldFindByStoreAndBatchId() {
        given(batchRepository.findByStock_Product_Store_IdAndId(storeId, batchId)).willReturn(Optional.of(batch));

        Batch result = findService.findByStoreAndBatchId(storeId, batchId);

        assertNotNull(batch);
        assertEquals(batchId, result.getId());

        verify(batchRepository).findByStock_Product_Store_IdAndId(storeId, batchId);
    }

    @Test
    void shouldNotFindStoreAndBatchId() {
        given(batchRepository.findByStock_Product_Store_IdAndId(storeId, batchId)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> findService.findByStoreAndBatchId(storeId, batchId));
    }
}