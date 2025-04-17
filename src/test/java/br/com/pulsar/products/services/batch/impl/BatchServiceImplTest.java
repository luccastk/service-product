package br.com.pulsar.products.services.batch.impl;

import br.com.pulsar.products.domain.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.domain.dtos.batch.UpdateBatchDTO;
import br.com.pulsar.products.domain.services.batch.impl.BatchServiceImpl;
import br.com.pulsar.products.factory.TestBatch;
import br.com.pulsar.products.factory.TestStock;
import br.com.pulsar.products.factory.TestStore;
import br.com.pulsar.products.domain.mappers.BatchMapper;
import br.com.pulsar.products.domain.models.Batch;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Stock;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.repositories.BatchRepository;
import br.com.pulsar.products.domain.services.find.FindService;
import br.com.pulsar.products.domain.services.stock.StockService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class BatchServiceImplTest {

    @InjectMocks
    private BatchServiceImpl batchService;

    @Mock
    private FindService findService;

    @Mock
    private StockService stockService;

    @Mock
    private BatchRepository batchRepository;

    @Mock
    private BatchMapper batchMapper;

    @Mock
    private Product product;
    
    @Captor
    private ArgumentCaptor<List<Batch>> listBatchCaptor;

    private Store store;
    private Stock stock;
    private Batch batch;
    private CreateBatchDTO createBatchDTO;
    private UpdateBatchDTO updateBatchDTO;

    @BeforeEach
    void setUp() {
        store = TestStore.createStore();
        stock = TestStock.createStock();
        batch = TestBatch.createBatch();
        
        batch.setStock(stock);
        
        stock.setStore(store);
        stock.setBatches(List.of(batch));

        createBatchDTO = TestBatch.createBatchDTO();

        updateBatchDTO = TestBatch.updateBatchDTO();
    }

    @Test
    void shouldCreateBatch() {
        given(batchMapper.toEntity(List.of(createBatchDTO))).willReturn(List.of(batch));
        doNothing().when(stockService).updateStockQuantity(stock, createBatchDTO.quantity());
        given(batchRepository.saveAll(List.of(batch))).willReturn(List.of(batch));

        List<Batch> result = batchService.createBatch(stock, List.of(createBatchDTO));

        then(batchRepository).should().saveAll(listBatchCaptor.capture());

        List<Batch> batchesSaved = listBatchCaptor.getValue();

        assertNotNull(result);
        assertEquals(result.get(0).getQuantity(), batchesSaved.get(0).getQuantity());
    }

    @Test
    void shouldGetBatchById() {
        given(findService.findByStoreAndBatchId(store.getId(), batch.getId())).willReturn(batch);

        Batch result = batchService.getId(store.getId(), batch.getId());

        assertNotNull(result);
    }

    @Test
    void shouldGetNotEntityFoundException(){
        given(findService.findByStoreAndBatchId(store.getId(), batch.getId())).willThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> batchService.getId(store.getId(), batch.getId()));
    }

    @Test
    void shouldUpdateBatchAndProduct() {
        given(findService.findByStoreAndBatchId(store.getId(), batch.getId())).willReturn(batch);
        given(batchRepository.save(batch)).willReturn(batch);

        Batch result = batchService.updateBatchAndProduct(store.getId(), batch.getId(), updateBatchDTO);

        assertNotNull(result);
        assertEquals(result.getQuantity(), updateBatchDTO.quantity());
        assertEquals(result.getValidity().getMonth(), updateBatchDTO.validity().getMonth());

        verify(batchRepository).save(batch);
    }

    @Test
    void shouldNotUpdateBatchNull() {
        UpdateBatchDTO newUpdateBatch = new UpdateBatchDTO(
                null,
                null
        );

        given(findService.findByStoreAndBatchId(store.getId(), batch.getId())).willReturn(batch);
        given(batchRepository.save(batch)).willReturn(batch);

        Batch result = batchService.updateBatchAndProduct(store.getId(), batch.getId(), newUpdateBatch);

        assertNotNull(result);
        assertEquals(result.getQuantity(), batch.getQuantity());
        assertEquals(result.getValidity(), batch.getValidity());

        verify(batchRepository).save(batch);
    }

    @Test
    void shouldGetBatchesForProduct() {
        given(product.getStock()).willReturn(stock);
        given(findService.findProductByStoreAndId(store.getId(), product.getId())).willReturn(product);

        List<Batch> result = batchService.getBatchesForProduct(store.getId(), product.getId());

        assertNotNull(result);
        assertEquals(result.get(0).getQuantity(), batch.getQuantity());
        assertEquals(result.get(0).getValidity(), batch.getValidity());
    }
}