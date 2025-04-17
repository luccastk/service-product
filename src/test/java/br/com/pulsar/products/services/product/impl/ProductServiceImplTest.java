package br.com.pulsar.products.services.product.impl;

import br.com.pulsar.products.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.dtos.csv.ProductCsvDTO;
import br.com.pulsar.products.dtos.http.ResponseProductDTO;
import br.com.pulsar.products.dtos.http.ResponseWrapperProductDTO;
import br.com.pulsar.products.dtos.products.CreateProductDTO;
import br.com.pulsar.products.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.dtos.products.UpdateProductDTO;
import br.com.pulsar.products.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.dtos.stock.StockDTO;
import br.com.pulsar.products.exceptions.DuplicationException;
import br.com.pulsar.products.mappers.CsvToDomainMapper;
import br.com.pulsar.products.mappers.ProductMapper;
import br.com.pulsar.products.models.Batch;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Stock;
import br.com.pulsar.products.models.Store;
import br.com.pulsar.products.repositories.ProductRepository;
import br.com.pulsar.products.services.batch.BatchService;
import br.com.pulsar.products.services.csv.CsvProcessor;
import br.com.pulsar.products.services.find.FindService;
import br.com.pulsar.products.services.stock.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private StockService stockService;

    @Mock
    private BatchService batchService;

    @Mock
    private FindService findService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CsvProcessor csvProcessor;

    @Mock
    private ProductWrapperDTO productWrapperDTO;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    private UUID storeId;
    private UUID productId;
    private Store store;
    private Product product;
    private Stock stock;
    private Batch batch;
    private CreateProductDTO createProductDTO;
    private CreateBatchDTO createBatchDTO;
    private UpdateProductDTO updateProductDTO;
    private ResponseProductDTO responseProductDTO;
    private ProductCsvDTO productCsvDTO;

    @BeforeEach
    void setUp() {
        storeId = UUID.randomUUID();
        productId = UUID.randomUUID();

        store = new Store();
        store.setId(storeId);

        createBatchDTO = new CreateBatchDTO(
                10,
                LocalDate.now().plusMonths(10)
        );

        CreateStockDTO createStockDTO = new CreateStockDTO(
                BigDecimal.valueOf(10)
        );

        createProductDTO = new CreateProductDTO(
                "Product Test",
                createStockDTO,
                List.of(createBatchDTO)
        );

        product = new Product();
        product.setId(productId);
        product.setName(createProductDTO.name());
        product.setStore(store);
        product.setActive(true);

        stock = new Stock();
        batch = new Batch();

        updateProductDTO = new UpdateProductDTO(
                "New product name",
                true
        );

        StockDTO stockDTO = new StockDTO(
                10,
                BigDecimal.valueOf(50)
        );

        responseProductDTO = new ResponseProductDTO(
                product.getId(),
                product.getName(),
                product.getActive(),
                stockDTO
        );

        productCsvDTO = new ProductCsvDTO();
        productCsvDTO.setName(createProductDTO.name());
        productCsvDTO.setPrice(createStockDTO.price());
        productCsvDTO.setBatchQuantity(createBatchDTO.quantity());
        productCsvDTO.setBatchValidity(createBatchDTO.validity());
    }

    @Test
    void shouldSaveProduct() {
        given(productRepository.existsByNameIgnoringCase(createProductDTO.name())).willReturn(false);

        given(productMapper.ToEntity(createProductDTO)).willReturn(product);
        given(productRepository.save(product)).willReturn(product);

        given(stockService.createStockForProduct(product, createProductDTO.stock())).willReturn(stock);

        given(batchService.createBatch(stock, List.of(createBatchDTO))).willReturn(List.of(batch));

        Product result = productService.saveProducts(store, createProductDTO);

        then(productRepository).should().save(productCaptor.capture());

        Product productSaved = productCaptor.getValue();

        assertNotNull(result);
        assertEquals(productSaved.getName(), createProductDTO.name());
    }

    @Test
    void shouldThrowDuplicationExceptionForProduct() {
        given(productRepository.existsByNameIgnoringCase(createProductDTO.name())).willReturn(true);

        assertThrows(DuplicationException.class, () -> productService.saveProducts(store, createProductDTO));
    }

    @Test
    void shouldUpdateProduct() {
        product.setActive(false);

        given(findService.findProductByStoreAndId(storeId, productId)).willReturn(product);
        given(productRepository.save(product)).willReturn(product);

        Product result = productService.updateProduct(storeId, productId, updateProductDTO);

        assertNotNull(result);
        assertEquals(updateProductDTO.name(), result.getName());
        assertTrue(result.getActive());

        verify(productRepository).save(product);
    }

    @Test
    void shouldNotChangeProductIfNameAndActiveIsNull() {

        UpdateProductDTO newUpdateProductDTO = new UpdateProductDTO(
            null,
            null
        );

        given(findService.findProductByStoreAndId(storeId, productId)).willReturn(product);
        given(productRepository.save(product)).willReturn(product);

        Product result = productService.updateProduct(storeId, productId, newUpdateProductDTO);

        assertEquals(result.getName(), "Product Test");
        assertTrue(result.getActive());

        verify(productRepository).save(product);
    }

    @Test
    void shouldReturnProductDetails() {
        given(findService.findProductByStoreAndId(storeId, productId)).willReturn(product);

        Product result = productService.productDetail(storeId, productId);

        assertNotNull(result);
    }

    @Test
    void shouldDeactivateProduct() {
        given(findService.findProductByStoreAndId(storeId, productId)).willReturn(product);
        given(productRepository.save(product)).willReturn(product);

        productService.deActiveProduct(storeId, productId);

        assertFalse(product.getActive());

        verify(productRepository).save(product);
    }

    @Test
    void shouldReturnListProductsWithCursorNull() {
        int limit = 50;

        given(productRepository.findProductPerStore(storeId, limit)).willReturn(List.of(product));
        given(productMapper.ToDTO(List.of(product))).willReturn(List.of(responseProductDTO));

        ResponseWrapperProductDTO result = productService.listProductPerStore(storeId, null, limit);

        assertNotNull(result);
        assertNull(result.nextCursor());
        assertEquals(result.products().get(0).name(), product.getName());

        verify(productRepository).findProductPerStore(storeId, limit);
    }

    @Test
    void shouldReturnListProductWithCursor() {
        int limit = 1;

        given(productRepository.findProductPerStoreAfterCursor(storeId, product.getName(), limit)).willReturn(List.of(product));
        given(productMapper.ToDTO(List.of(product))).willReturn(List.of(responseProductDTO));

        ResponseWrapperProductDTO result = productService.listProductPerStore(storeId, product.getName(), limit);

        assertNotNull(result);
        assertEquals(result.nextCursor(), product.getName());
        assertEquals(result.products().get(0).name(), product.getName());

        verify(productRepository).findProductPerStoreAfterCursor(storeId, product.getName(), limit);
    }

    @Test
    void shouldReturnListEmptyProducts() {
        int limit = 50;

        given(productRepository.findProductPerStore(storeId, limit)).willReturn(List.of());
        given(productMapper.ToDTO(List.of())).willReturn(List.of());

        ResponseWrapperProductDTO result = productService.listProductPerStore(storeId, null, limit);

        assertNotNull(result);
        assertTrue(result.products().isEmpty());

        verify(productRepository).findProductPerStore(storeId, limit);
    }

    @Test
    void shouldParseCsvToCreateProduct() {

        String csv = """
                name,price,quantity,validity
                Product test csv,100.00,10,2025-10-10
                """;

        InputStream is = new ByteArrayInputStream(csv.getBytes());

        given(productRepository.existsByNameIgnoringCase(createProductDTO.name())).willReturn(false);
        given(productMapper.ToEntity(createProductDTO)).willReturn(product);

        given(stockService.createStockForProduct(product, createProductDTO.stock())).willReturn(stock);

        given(batchService.createBatch(stock, List.of(createBatchDTO))).willReturn(List.of(batch));

        when(csvProcessor.parse(is)).thenReturn(List.of(productCsvDTO));

        given(productRepository.save(product)).willReturn(product);

        ProductWrapperDTO wrapper = CsvToDomainMapper.toWrapper(List.of(productCsvDTO));

        productService.convertCsvToEntity(storeId, is);

        verify(productRepository, times(wrapper.products().size())).save(product);
    }
}