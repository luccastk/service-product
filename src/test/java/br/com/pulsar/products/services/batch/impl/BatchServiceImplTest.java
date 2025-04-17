package br.com.pulsar.products.services.batch.impl;

import br.com.pulsar.products.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.dtos.batch.UpdateBatchDTO;
import br.com.pulsar.products.mappers.BatchMapper;
import br.com.pulsar.products.models.Batch;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Stock;
import br.com.pulsar.products.repositories.BatchRepository;
import br.com.pulsar.products.services.find.FindService;
import br.com.pulsar.products.services.stock.StockService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    @Mock
    private Stock stock;

    @Captor
    private ArgumentCaptor<List<Batch>> listBatchCaptor;

    private UUID storeId;
    private UUID productId;
    private UUID batchId;
    private Batch batch;
    private CreateBatchDTO createBatchDTO;
    private UpdateBatchDTO updateBatchDTO;

    @BeforeEach
    void setUp() {
        storeId = UUID.randomUUID();
        productId = UUID.randomUUID();
        batchId = UUID.randomUUID();

        createBatchDTO = new CreateBatchDTO(
                100,
                LocalDate.now().plusMonths(10)
        );

        batch = new Batch();
        batch.setId(batchId);
        batch.setQuantity(createBatchDTO.quantity());
        batch.setValidity(createBatchDTO.validity());
        batch.setStock(stock);

        updateBatchDTO = new UpdateBatchDTO(
                50,
                LocalDate.now().plusMonths(6)
        );
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
        given(findService.findByStoreAndBatchId(storeId, batchId)).willReturn(batch);

        Batch result = batchService.getId(storeId, batchId);

        assertNotNull(result);
    }

    @Test
    void shouldGetNotEntityFoundException(){
        given(findService.findByStoreAndBatchId(storeId, batchId)).willThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> batchService.getId(storeId, batchId));
    }

    @Test
    void shouldUpdateBatchAndProduct() {
        given(findService.findByStoreAndBatchId(storeId, batchId)).willReturn(batch);
        given(batchRepository.save(batch)).willReturn(batch);

        Batch result = batchService.updateBatchAndProduct(storeId, batchId, updateBatchDTO);

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

        given(findService.findByStoreAndBatchId(storeId, batchId)).willReturn(batch);
        given(batchRepository.save(batch)).willReturn(batch);

        Batch result = batchService.updateBatchAndProduct(storeId, batchId, newUpdateBatch);

        assertNotNull(result);
        assertEquals(result.getQuantity(), batch.getQuantity());
        assertEquals(result.getValidity(), batch.getValidity());

        verify(batchRepository).save(batch);
    }

    @Test
    void shouldGetBatchesForProduct() {
        given(product.getStock()).willReturn(stock);
        given(stock.getBatches()).willReturn(List.of(batch));
        given(findService.findProductByStoreAndId(storeId, productId)).willReturn(product);

        List<Batch> result = batchService.getBatchesForProduct(storeId, productId);

        assertNotNull(result);
        assertEquals(result.get(0).getQuantity(), batch.getQuantity());
        assertEquals(result.get(0).getValidity(), batch.getValidity());
    }
}