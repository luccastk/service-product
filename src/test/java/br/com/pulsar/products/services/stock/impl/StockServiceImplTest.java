package br.com.pulsar.products.services.stock.impl;

import br.com.pulsar.products.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.dtos.stock.UpdateStockDTO;
import br.com.pulsar.products.mappers.StockMapper;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Stock;
import br.com.pulsar.products.models.Store;
import br.com.pulsar.products.repositories.StockRepository;
import br.com.pulsar.products.services.find.FindService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private FindService findService;

    @Mock
    private StockMapper stockMapper;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private CreateStockDTO createStockDTO;

    @Mock
    private CreateBatchDTO createBatchDTO;

    private UUID storeId;
    private UUID productId;
    private Product product;
    private Stock stock;
    private UpdateStockDTO updateStockDTO;

    @BeforeEach
    void setUp() {
        storeId = UUID.randomUUID();

        Store store = new Store();
        store.setId(storeId);

        product = new Product();
        product.setId(productId);

        store.setProducts(List.of(product));

        stock = new Stock();
        stock.setStore(store);
        stock.setQuantity(50);
        stock.setPrice(BigDecimal.valueOf(20));

        product.setStock(stock);

        updateStockDTO = new UpdateStockDTO(
                BigDecimal.valueOf(90)
        );
    }

    @Test
    void shouldCreateStockForProduct() {
        given(stockMapper.toEntity(createStockDTO)).willReturn(stock);

        Stock result = stockService.createStockForProduct(product, createStockDTO);

        assertNotNull(result);
        assertEquals(result, product.getStock());
    }

    @Test
    void shouldUpdateStockQuantityIfQuantityEqualsNull() {
        stock.setQuantity(null);

        given(stockRepository.save(stock)).willReturn(stock);

        stockService.updateStockQuantity(stock, 10);

        assertNotNull(stock);
        assertEquals(stock.getQuantity(), 10);

        verify(stockRepository).save(stock);
    }

    @Test
    void shouldUpdateStockQuantityAndSum() {
        given(stockRepository.save(stock)).willReturn(stock);

        stockService.updateStockQuantity(stock, 30);

        assertNotNull(stock);
        assertEquals(stock.getQuantity(), 80);

        verify(stockRepository).save(stock);
    }

    @Test
    void shouldUpdatePrice() {
        given(findService.findProductByStoreAndId(storeId, productId)).willReturn(product);
        given(stockRepository.save(stock)).willReturn(stock);

        Stock result = stockService.updatePrice(storeId, productId, updateStockDTO);

        assertNotNull(result);
        assertEquals(result.getPrice(), updateStockDTO.price());

        verify(stockRepository).save(stock);
    }

    @Test
    void shouldNotUpdatePriceIfNull() {
        UpdateStockDTO newUpdateStock = new UpdateStockDTO(
                null
        );

        given(findService.findProductByStoreAndId(storeId, productId)).willReturn(product);
        given(stockRepository.save(stock)).willReturn(stock);

        Stock result = stockService.updatePrice(storeId, productId, newUpdateStock);

        assertNotNull(result);
        assertEquals(result.getPrice(), BigDecimal.valueOf(20));

        verify(stockRepository).save(stock);
    }
}