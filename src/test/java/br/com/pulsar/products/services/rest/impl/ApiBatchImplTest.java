package br.com.pulsar.products.services.rest.impl;

import br.com.pulsar.products.domain.dtos.batch.BatchWrapperDTO;
import br.com.pulsar.products.domain.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.domain.dtos.batch.UpdateBatchDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseBatchDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseWrapperBatchDTO;
import br.com.pulsar.products.domain.services.rest.impl.ApiBatchImpl;
import br.com.pulsar.products.factory.TestBatch;
import br.com.pulsar.products.factory.TestProduct;
import br.com.pulsar.products.factory.TestStock;
import br.com.pulsar.products.factory.TestStore;
import br.com.pulsar.products.domain.mappers.BatchMapper;
import br.com.pulsar.products.domain.models.Batch;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Stock;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.services.batch.BatchService;
import br.com.pulsar.products.domain.services.find.FindService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.pulsar.products.factory.TestProduct.createProduct;
import static br.com.pulsar.products.factory.TestStock.createStock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ApiBatchImplTest {

    @InjectMocks
    private ApiBatchImpl restBatch;

    @Mock
    private FindService findService;

    @Mock
    private BatchMapper batchMapper;

    @Mock
    private BatchService batchService;

    private Store store;
    private Product product;
    private Stock stock;
    private Batch batch;
    private CreateBatchDTO createBatchDTO;
    private BatchWrapperDTO batchWrapperDTO;
    private ResponseBatchDTO responseBatchDTO;
    private ResponseWrapperBatchDTO responseWrapperBatchDTO;
    private UpdateBatchDTO updateBatchDTO;
    private ResponseBatchDTO updateResponseBatchDTO;

    @BeforeEach
    void setUp() {
        store = TestStore.createStore();
        product = TestProduct.createProduct();
        batch = TestBatch.createBatch();
        stock = TestStock.createStock();

        product.setStock(stock);

        createBatchDTO = TestBatch.createBatchDTO();
        batchWrapperDTO = TestBatch.batchWrapperDTO();

        responseBatchDTO = new ResponseBatchDTO(
                batch.getId(),
                createProduct().getId(),
                createProduct().getName(),
                createStock().getQuantity(),
                batch.getValidity(),
                LocalDateTime.now()
        );

        responseWrapperBatchDTO = new ResponseWrapperBatchDTO(
                List.of(responseBatchDTO)
        );

        updateBatchDTO = TestBatch.updateBatchDTO();

        updateResponseBatchDTO = TestBatch.updatedResponseBatchDTO();
    }

    @Test
    void shouldAddBatchProduct() {
        given(findService.findProductByStoreAndId(store.getId(), product.getId())).willReturn(product);
        given(batchService.createBatch(stock, List.of(createBatchDTO))).willReturn(List.of(batch));
        given(batchMapper.toDTOResponse(List.of(batch))).willReturn(List.of(responseBatchDTO));

        ResponseWrapperBatchDTO result = restBatch.addBatchProduct(store.getId(), product.getId(), batchWrapperDTO);

        assertNotNull(result);
        assertEquals(result.batches().get(0).name(), product.getName());
    }

    @Test
    void shouldGetBatchById() {

        given(batchService.getId(store.getId(), batch.getId())).willReturn(batch);
        given(batchMapper.toDTOResponse(batch)).willReturn(responseBatchDTO);

        ResponseWrapperBatchDTO result = restBatch.getBatchById(store.getId(), batch.getId());

        assertNotNull(result);
    }

    @Test
    void shouldUpdateBatch() {
        batch.setQuantity(updateBatchDTO.quantity());
        batch.setValidity(updateBatchDTO.validity());

        given(batchService.updateBatchAndProduct(store.getId(), product.getId(), updateBatchDTO)).willReturn(batch);
        given(batchMapper.toDTOResponse(batch)).willReturn(updateResponseBatchDTO);

        ResponseWrapperBatchDTO result = restBatch.updateBatch(store.getId(), product.getId(), updateBatchDTO);

        assertNotNull(result);
        assertEquals(result.batches().get(0).quantity(), updateBatchDTO.quantity());
        assertEquals(result.batches().get(0).validity(), updateBatchDTO.validity());
    }

    @Test
    void listBatchesByProductId() {
        given(batchService.getBatchesForProduct(store.getId(), product.getId())).willReturn(List.of(batch));
        given(batchMapper.toDTOResponse(List.of(batch))).willReturn(List.of(responseBatchDTO));

        ResponseWrapperBatchDTO result = restBatch.listBatchesByProductId(store.getId(), product.getId());

        assertNotNull(result);
        assertEquals(result.batches().get(0).name(), product.getName());
        assertEquals(result.batches().get(0).batchId(), batch.getId());
        assertEquals(result.batches().get(0).quantity(), batch.getQuantity());
        assertEquals(result.batches().get(0).validity().getMonth(), batch.getValidity().getMonth());
    }
}