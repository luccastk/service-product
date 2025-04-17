package br.com.pulsar.products.services.rest.impl;

import br.com.pulsar.products.dtos.batch.BatchWrapperDTO;
import br.com.pulsar.products.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.dtos.http.ResponseBatchDTO;
import br.com.pulsar.products.dtos.http.ResponseWrapperBatchDTO;
import br.com.pulsar.products.factory.TestBatch;
import br.com.pulsar.products.factory.TestProduct;
import br.com.pulsar.products.factory.TestStock;
import br.com.pulsar.products.factory.TestStore;
import br.com.pulsar.products.mappers.BatchMapper;
import br.com.pulsar.products.models.Batch;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Stock;
import br.com.pulsar.products.models.Store;
import br.com.pulsar.products.services.batch.BatchService;
import br.com.pulsar.products.services.find.FindService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

    @BeforeEach
    void setUp() {
        store = TestStore.createValidStore();
        product = TestProduct.createProduct();
        batch = TestBatch.batch();
        stock = TestStock.stock();

        product.setStock(stock);

        createBatchDTO = TestBatch.createBatchDTO();
        batchWrapperDTO = TestBatch.batchWrapperDTO();

        responseBatchDTO = TestBatch.responseBatchDTO();
        responseWrapperBatchDTO = TestBatch.responseWrapperBatchDTO();
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

        ResponseWrapperBatchDTO result = restBatch.getBatchById(store.getId(), product.getId());

        assertNotNull(result);
    }

    @Test
    void updateBatch() {
    }

    @Test
    void listBatchesByProductId() {
    }
}