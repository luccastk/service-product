package br.com.pulsar.products.services.product.impl;

import br.com.pulsar.products.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.dtos.products.CreateProductDTO;
import br.com.pulsar.products.dtos.products.UpdateProductDTO;
import br.com.pulsar.products.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.mappers.BatchMapper;
import br.com.pulsar.products.mappers.ProductMapper;
import br.com.pulsar.products.mappers.StockMapper;
import br.com.pulsar.products.models.Batch;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Stock;
import br.com.pulsar.products.models.Store;
import br.com.pulsar.products.repositories.ProductRepository;
import br.com.pulsar.products.services.batch.BatchService;
import br.com.pulsar.products.services.stock.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    private UUID storeId;
    private UUID productId;
    private Store store;
    private Product product;
    private Stock stock;
    private Batch batch;
    private CreateProductDTO createProductDTO;
    private CreateStockDTO createStockDTO;
    private CreateBatchDTO createBatchDTO;
    private UpdateProductDTO updateProductDTO;

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

        createStockDTO = new CreateStockDTO(
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

        stock = new Stock();
        batch = new Batch();

        updateProductDTO = new UpdateProductDTO(
                "New product name",
                true
        );
    }

    @Test
    void shouldSaveProduct() {
        given(productRepository.existsByNameIgnoringCase(createProductDTO.name())).willReturn(false);

        given(productMapper.ToEntity(createProductDTO)).willReturn(product);
        given(productRepository.save(product)).willReturn(product);

        given(stockService.createStockForProduct(product, createProductDTO)).willReturn(stock);

        given(batchService.createBatch(stock, List.of(createBatchDTO))).willReturn(List.of(batch));

        Product result = productService.saveProducts(store, createProductDTO);

        then(productRepository).should().save(productCaptor.capture());

        Product productSaved = productCaptor.getValue();

        assertNotNull(result);
        assertEquals(productSaved.getName(), createProductDTO.name());
    }

    @Test
    void name() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void productDetail() {
    }

    @Test
    void deActiveProduct() {
    }

    @Test
    void listProductPerStore() {
    }
}