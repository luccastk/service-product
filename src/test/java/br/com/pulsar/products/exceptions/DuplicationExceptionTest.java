package br.com.pulsar.products.exceptions;

import br.com.pulsar.products.domain.dtos.products.CreateProductDTO;
import br.com.pulsar.products.domain.mappers.ProductMapper;
import br.com.pulsar.products.domain.models.Batch;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Stock;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.repositories.ProductRepository;
import br.com.pulsar.products.domain.services.batch.BatchService;
import br.com.pulsar.products.domain.services.product.impl.ProductServiceImpl;
import br.com.pulsar.products.domain.services.stock.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class DuplicationExceptionTest {

    private CreateProductDTO createProductDTO;
    private Store store;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private StockService stockService;

    @Mock
    private BatchService batchService;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        store = new Store();
        store.setPk(1L);
        store.setId(UUID.randomUUID());
        store.setName("Store test");
        store.setActive(true);
        store.setProducts(null);

        createProductDTO = new CreateProductDTO(
                 "Product test",
                null,
                null
        );
    }

    @Test
    void shouldNotThrowDuplicateException() {
        Product product = new Product();
        Stock stock = new Stock();
        Batch batch = new Batch();

        given(productRepository.existsByNameIgnoringCase(createProductDTO.name())).willReturn(false);
        given(productMapper.ToEntity(createProductDTO)).willReturn(product);
        given(stockService.createStockForProduct(product, createProductDTO.stock())).willReturn(stock);
        given(batchService.createBatch(stock, createProductDTO.batch())).willReturn(List.of(batch));

        assertDoesNotThrow(() -> productService.saveProducts(store, createProductDTO));
    }

    @Test
    void shouldThrowDuplicateException() {

        given(productRepository.existsByNameIgnoringCase(createProductDTO.name())).willReturn(true);

        assertThrows(DuplicationException.class, () -> productService.saveProducts(store, createProductDTO));
    }
}