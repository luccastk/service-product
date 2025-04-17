package br.com.pulsar.products.services.product.impl;

import br.com.pulsar.products.domain.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.domain.dtos.csv.ProductCsvDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseProductDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseWrapperProductDTO;
import br.com.pulsar.products.domain.dtos.products.CreateProductDTO;
import br.com.pulsar.products.domain.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.domain.dtos.products.UpdateProductDTO;
import br.com.pulsar.products.domain.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.domain.dtos.stock.StockDTO;
import br.com.pulsar.products.domain.services.product.impl.ProductServiceImpl;
import br.com.pulsar.products.exceptions.DuplicationException;
import br.com.pulsar.products.factory.*;
import br.com.pulsar.products.domain.mappers.CsvToDomainMapper;
import br.com.pulsar.products.domain.mappers.ProductMapper;
import br.com.pulsar.products.domain.models.Batch;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Stock;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.repositories.ProductRepository;
import br.com.pulsar.products.domain.services.batch.BatchService;
import br.com.pulsar.products.domain.services.csv.CsvProcessor;
import br.com.pulsar.products.domain.services.find.FindService;
import br.com.pulsar.products.domain.services.stock.StockService;
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
import java.util.List;

import static br.com.pulsar.products.factory.TestBatch.createBatchDTO;
import static br.com.pulsar.products.factory.TestStore.*;
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
        store = createStore();
        product = TestProduct.createProduct();
        stock = TestStock.createStock();
        batch = TestBatch.createBatch();
        
        product.setStore(store);

        createBatchDTO = createBatchDTO();

        CreateStockDTO createStockDTO = TestStock.createStockDTO();

        createProductDTO = TestProduct.createProductDTO();


        updateProductDTO = TestProduct.updateProductDTO();

        StockDTO stockDTO = TestStock.stockDTO();

        responseProductDTO = new ResponseProductDTO(
                product.getId(),
                product.getName(),
                product.getActive(),
                stockDTO
        );

        productCsvDTO = TestCsvProducts.createProductCsvDTO();
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

        given(findService.findProductByStoreAndId(store.getId(), product.getId())).willReturn(product);
        given(productRepository.save(product)).willReturn(product);

        Product result = productService.updateProduct(store.getId(), product.getId(), updateProductDTO);

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

        given(findService.findProductByStoreAndId(store.getId(), product.getId())).willReturn(product);
        given(productRepository.save(product)).willReturn(product);

        Product result = productService.updateProduct(store.getId(), product.getId(), newUpdateProductDTO);

        assertEquals(result.getName(), product.getName());
        assertTrue(result.getActive());

        verify(productRepository).save(product);
    }

    @Test
    void shouldReturnProductDetails() {
        given(findService.findProductByStoreAndId(store.getId(), product.getId())).willReturn(product);

        Product result = productService.productDetail(store.getId(), product.getId());

        assertNotNull(result);
    }

    @Test
    void shouldDeactivateProduct() {
        given(findService.findProductByStoreAndId(store.getId(), product.getId())).willReturn(product);
        given(productRepository.save(product)).willReturn(product);

        productService.deActiveProduct(store.getId(), product.getId());

        assertFalse(product.getActive());

        verify(productRepository).save(product);
    }

    @Test
    void shouldReturnListProductsWithCursorNull() {
        int limit = 50;

        given(productRepository.findProductPerStore(store.getId(), limit)).willReturn(List.of(product));
        given(productMapper.ToDTO(List.of(product))).willReturn(List.of(responseProductDTO));

        ResponseWrapperProductDTO result = productService.listProductPerStore(store.getId(), null, limit);

        assertNotNull(result);
        assertNull(result.nextCursor());
        assertEquals(result.products().get(0).name(), product.getName());

        verify(productRepository).findProductPerStore(store.getId(), limit);
    }

    @Test
    void shouldReturnListProductWithCursor() {
        int limit = 1;

        given(productRepository.findProductPerStoreAfterCursor(store.getId(), product.getName(), limit)).willReturn(List.of(product));
        given(productMapper.ToDTO(List.of(product))).willReturn(List.of(responseProductDTO));

        ResponseWrapperProductDTO result = productService.listProductPerStore(store.getId(), product.getName(), limit);

        assertNotNull(result);
        assertEquals(result.nextCursor(), product.getName());
        assertEquals(result.products().get(0).name(), product.getName());

        verify(productRepository).findProductPerStoreAfterCursor(store.getId(), product.getName(), limit);
    }

    @Test
    void shouldReturnListEmptyProducts() {
        int limit = 50;

        given(productRepository.findProductPerStore(store.getId(), limit)).willReturn(List.of());
        given(productMapper.ToDTO(List.of())).willReturn(List.of());

        ResponseWrapperProductDTO result = productService.listProductPerStore(store.getId(), null, limit);

        assertNotNull(result);
        assertTrue(result.products().isEmpty());

        verify(productRepository).findProductPerStore(store.getId(), limit);
    }

    @Test
    void shouldParseCsvToCreateProduct() {

        String csv = """
                name,price,quantity,validity
                Product test,100.00,10,2025-10-10
                """;

        InputStream is = new ByteArrayInputStream(csv.getBytes());

        given(productRepository.existsByNameIgnoringCase(createProductDTO.name())).willReturn(false);
        given(productMapper.ToEntity(createProductDTO)).willReturn(product);

        given(stockService.createStockForProduct(product, createProductDTO.stock())).willReturn(stock);

        given(batchService.createBatch(stock, List.of(createBatchDTO))).willReturn(List.of(batch));

        when(csvProcessor.parse(is)).thenReturn(List.of(productCsvDTO));

        given(productRepository.save(product)).willReturn(product);

        ProductWrapperDTO wrapper = CsvToDomainMapper.toWrapper(List.of(productCsvDTO));

        productService.convertCsvToEntity(store.getId(), is);

        verify(productRepository, times(wrapper.products().size())).save(product);
    }
}