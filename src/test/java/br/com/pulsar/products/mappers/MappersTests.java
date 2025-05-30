package br.com.pulsar.products.mappers;

import br.com.pulsar.products.domain.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.domain.dtos.csv.ProductCsvDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseBatchDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseProductDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseStoreDTO;
import br.com.pulsar.products.domain.dtos.products.CreateProductDTO;
import br.com.pulsar.products.domain.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.domain.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.domain.dtos.stock.StockDTO;
import br.com.pulsar.products.domain.dtos.store.CreateStoreDTO;
import br.com.pulsar.products.domain.mappers.*;
import br.com.pulsar.products.domain.models.Batch;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Stock;
import br.com.pulsar.products.domain.models.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static br.com.pulsar.products.factory.TestBatch.*;
import static br.com.pulsar.products.factory.TestCsvProducts.*;
import static br.com.pulsar.products.factory.TestProduct.*;
import static br.com.pulsar.products.factory.TestStock.*;
import static br.com.pulsar.products.factory.TestStore.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MappersTests {

    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private BatchMapper batchMapper;

    private Store store;
    private Product product;
    private Stock stock;
    private Batch batch;

    private CreateStockDTO createStockDTO;
    private CreateBatchDTO createBatchDTO;
    private CreateProductDTO createProductDTO;
    private CreateStoreDTO createStoreDTO;
    private ProductCsvDTO productCsvDTO;

    @BeforeEach
    void setUp() {

        store = createStore();
        product = createProduct();
        stock = createStock();
        batch = createBatch();

        store.setProducts(List.of(product));
        product.setStore(store);
        product.setStock(stock);

        stock.setStore(store);
        stock.setProduct(product);
        stock.setBatches(List.of(batch));

        createStockDTO = createStockDTO();

        createBatchDTO = createBatchDTO();

        createProductDTO = createProductDTO();

        createStoreDTO = createStoreDTO();

        productCsvDTO = createProductCsvDTO();
    }

    @Test
    void shouldMapperStore() {
        Store store1 = storeMapper.ToEntity(createStoreDTO);

        assertNotNull(store1);
        assertEquals(store1.getName(), createStoreDTO.name());
    }

    @Test
    void shouldReturnStoreNull() {
        Store store1 = storeMapper.ToEntity(null);

        assertNull(store1);
    }

    @Test
    void shouldMapperResponseStore() {
        ResponseStoreDTO responseStoreDTO = storeMapper.ToDTO(store);

        assertNotNull(responseStoreDTO);
        assertEquals(responseStoreDTO.name(), store.getName());
    }

    @Test
    void shouldReturnResponseNull() {
        ResponseStoreDTO responseStoreDTO = storeMapper.ToDTO((Store) null);

        assertNull(responseStoreDTO);
    }

    @Test
    void shouldMapperListResponseStore() {

        List<ResponseStoreDTO> responseStoreDTOList = storeMapper.ToDTO(List.of(store));

        assertNotNull(responseStoreDTOList);
        assertEquals(responseStoreDTOList.get(0).name(), store.getName());
    }

    @Test
    void shouldReturnResponseListNull() {
        List<ResponseStoreDTO> responseStoreDTOList = storeMapper.ToDTO((List<Store>) null);

        assertNull(responseStoreDTOList);
    }

    @Test
    void shouldMapperProduct() {
        Product product1 = productMapper.ToEntity(createProductDTO);

        assertNotNull(product1);
        assertEquals(product1.getName(), createProductDTO.name());
    }

    @Test
    void shouldReturnProductNull() {
        Product product1 = productMapper.ToEntity((CreateProductDTO) null);

        assertNull(product1);
    }

    @Test
    void shouldMapperProductList() {
        List<Product> products = productMapper.ToEntity(List.of(createProductDTO));

        assertNotNull(products);
        assertEquals(products.get(0).getName(), createProductDTO.name());
    }

    @Test
    void shouldReturnProductListNull() {
        List<Product> products = productMapper.ToEntity((List<CreateProductDTO>) null);

        assertNull(products);
    }

    @Test
    void shouldMapperProductToResponseProduct() {
        ResponseProductDTO responseProductDTO = productMapper.ToDTO(product);

        assertNotNull(responseProductDTO);
        assertEquals(responseProductDTO.name(), product.getName());
        assertEquals(responseProductDTO.stocks().price(), stock.getPrice());
    }

    @Test
    void shouldResponseProductResponseNull() {
        ResponseProductDTO responseProductDTO = productMapper.ToDTO((Product) null);

        assertNull(responseProductDTO);
    }

    @Test
    void shouldMapperProductListToResponseProduct() {
        List<ResponseProductDTO> responseProductDTOS = productMapper.ToDTO(List.of(product));

        assertNotNull(responseProductDTOS);
        assertEquals(responseProductDTOS.get(0).name(), product.getName());
    }

    @Test
    void shouldReturnProductListResponseNull() {
        List<ResponseProductDTO> responseProductDTOS = productMapper.ToDTO((List<Product>) null);

        assertNull(responseProductDTOS);
    }

    @Test
    void shouldMapperStock() {
        Stock stock1 = stockMapper.toEntity(createStockDTO);

        assertNotNull(stock1);
        assertEquals(stock1.getPrice(), createStockDTO.price());
    }

    @Test
    void shouldReturnStockNull() {
        Stock stock1 = stockMapper.toEntity(null);

        assertNull(stock1);
    }

    @Test
    void shouldMapperStockDto() {
        StockDTO stockDTO = stockMapper.toDTO(stock);

        assertNotNull(stockDTO);
        assertEquals(stockDTO.price(), stock.getPrice());
    }

    @Test
    void shouldReturnStockResponseNull() {
        StockDTO stockDTO = stockMapper.toDTO(null);

        assertNull(stockDTO);
    }

    @Test
    void shouldMapperBatch() {
        Batch batch1 = batchMapper.toEntity(createBatchDTO);

        assertNotNull(batch1);
        assertEquals(batch1.getQuantity(), createBatchDTO.quantity());
        assertEquals(batch1.getValidity(), createBatchDTO.validity());
    }

    @Test
    void shouldReturnBatchNull() {
        Batch batch1 = batchMapper.toEntity((CreateBatchDTO) null);

        assertNull(batch1);
    }

    @Test
    void shouldMapperBatchesList() {
        List<Batch> batches = batchMapper.toEntity(List.of(createBatchDTO));

        assertNotNull(batches);
        assertEquals(batches.get(0).getQuantity(), createBatchDTO.quantity());
        assertEquals(batches.get(0).getValidity(), createBatchDTO.validity());
    }

    @Test
    void shouldReturnBatchesListNull() {
        List<Batch> batches = batchMapper.toEntity((List<CreateBatchDTO>) null);

        assertNull(batches);
    }

    @Test
    void shouldMapperBatchToResponseBatch() {
        ResponseBatchDTO responseBatchDTO = batchMapper.toDTOResponse(batch);

        assertNotNull(responseBatchDTO);
        assertEquals(responseBatchDTO.batchId(), batch.getId());
    }

    @Test
    void shouldReturnBatchResponseNull() {
        ResponseBatchDTO responseBatchDTO = batchMapper.toDTOResponse((Batch) null);

        assertNull(responseBatchDTO);
    }

    @Test
    void shouldMapperBatchesToResponseBatches() {
        List<ResponseBatchDTO> responseBatchDTOS = batchMapper.toDTOResponse(List.of(batch));

        assertNotNull(responseBatchDTOS);
        assertEquals(responseBatchDTOS.get(0).batchId(), batch.getId());
    }

    @Test
    void shouldReturnBatchListResponseNull() {
        List<ResponseBatchDTO> responseBatchDTOS = batchMapper.toDTOResponse((List<Batch>) null);

        assertNull(responseBatchDTOS);
    }
    @Test
    void shouldReturnNullWhenStockIsNull() {
        batch.setStock(null);

        ResponseBatchDTO result = batchMapper.toDTOResponse(batch);
        assertNull(result.productId());
        assertNull(result.name());
    }

    @Test
    void shouldReturnNullWhenProductIsNull() {
        stock.setProduct(null);

        batch.setStock(stock);

        ResponseBatchDTO result = batchMapper.toDTOResponse(batch);
        assertNull(result.productId());
        assertNull(result.name());
    }

    @Test
    void shouldMapperCsvProduct() {
        CreateProductDTO createProductDTO1 = CsvToDomainMapper.map(productCsvDTO);

        assertNotNull(createProductDTO1);
        assertEquals(createProductDTO1.name(), productCsvDTO.getName());
    }

    @Test
    void shouldMapperCsvProductList() {
        ProductWrapperDTO productDTOS = CsvToDomainMapper.toWrapper(List.of(productCsvDTO));

        assertNotNull(productDTOS);
        assertEquals(productDTOS.products().get(0).name(), productCsvDTO.getName());
    }
}