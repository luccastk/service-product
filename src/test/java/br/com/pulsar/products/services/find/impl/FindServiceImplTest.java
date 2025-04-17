package br.com.pulsar.products.services.find.impl;

import br.com.pulsar.products.domain.services.find.impl.FindServiceImpl;
import br.com.pulsar.products.factory.TestBatch;
import br.com.pulsar.products.factory.TestProduct;
import br.com.pulsar.products.factory.TestStore;
import br.com.pulsar.products.domain.models.Batch;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.repositories.BatchRepository;
import br.com.pulsar.products.domain.repositories.ProductRepository;
import br.com.pulsar.products.domain.repositories.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Optional;

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

    private Store store;
    private Product product;
    private Batch batch;

    @BeforeEach
    void setUp() {
        store = TestStore.createStore();

        product = TestProduct.createProduct();
        product.setStore(store);

        batch = TestBatch.createBatch();
    }

    @Test
    void shouldFindProductByStoreAndId() {
        given(productRepository.findByStore_IdAndId(store.getId(), product.getId())).willReturn(Optional.of(product));

        Product result = findService.findProductByStoreAndId(store.getId(), product.getId());

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());

        verify(productRepository).findByStore_IdAndId(store.getId(), product.getId());
    }

    @Test
    void shouldNotFindProductByStoreAndId() {
        given(productRepository.findByStore_IdAndId(store.getId(), product.getId()))
                .willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                findService.findProductByStoreAndId(store.getId(), product.getId())
        );
    }

    @Test
    void shouldFindStoreId() {
        given(storeRepository.findByUuid(store.getId())).willReturn(Optional.of(store));

        Store result = findService.findStoreId(store.getId());

        assertNotNull(result);
        assertEquals(store.getId(), result.getId());

        verify(storeRepository).findByUuid(store.getId());
    }

    @Test
    void shouldNotFindStoreId() {
        given(storeRepository.findByUuid(store.getId())).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> findService.findStoreId(store.getId()));
    }

    @Test
    void shouldFindByStoreAndBatchId() {
        given(batchRepository.findByStock_Product_Store_IdAndId(store.getId(), batch.getId())).willReturn(Optional.of(batch));

        Batch result = findService.findByStoreAndBatchId(store.getId(), batch.getId());

        assertNotNull(batch);
        assertEquals(batch.getId(), result.getId());

        verify(batchRepository).findByStock_Product_Store_IdAndId(store.getId(), batch.getId());
    }

    @Test
    void shouldNotFindStoreAndBatchId() {
        given(batchRepository.findByStock_Product_Store_IdAndId(store.getId(), batch.getId())).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> findService.findByStoreAndBatchId(store.getId(), batch.getId()));
    }
}