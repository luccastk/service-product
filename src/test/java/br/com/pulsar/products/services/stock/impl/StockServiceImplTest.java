package br.com.pulsar.products.services.stock.impl;

import br.com.pulsar.products.domain.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.domain.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.domain.dtos.stock.UpdateStockDTO;
import br.com.pulsar.products.domain.services.stock.impl.StockServiceImpl;
import br.com.pulsar.products.factory.TestProduct;
import br.com.pulsar.products.factory.TestStock;
import br.com.pulsar.products.factory.TestStore;
import br.com.pulsar.products.domain.mappers.StockMapper;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Stock;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.repositories.StockRepository;
import br.com.pulsar.products.domain.services.find.FindService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

    private Store store;
    private Product product;
    private Stock stock;
    private UpdateStockDTO updateStockDTO;

    @BeforeEach
    void setUp() {
        store = TestStore.createStore();
        product = TestProduct.createProduct();
        stock = TestStock.createStock();

        store.setProducts(List.of(product));

        product.setStock(stock);

        stock.setStore(store);

        updateStockDTO = TestStock.updateStockDTO();
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
        assertEquals(10, stock.getQuantity());

        verify(stockRepository).save(stock);
    }

    @Test
    void shouldUpdateStockQuantityAndSum() {
        given(stockRepository.save(stock)).willReturn(stock);

        stockService.updateStockQuantity(stock, 30);

        assertNotNull(stock);
        assertEquals(130, stock.getQuantity());

        verify(stockRepository).save(stock);
    }

    @Test
    void shouldUpdatePrice() {
        given(findService.findProductByStoreAndId(store.getId(), product.getId())).willReturn(product);
        given(stockRepository.save(stock)).willReturn(stock);

        Stock result = stockService.updatePrice(store.getId(), product.getId(), updateStockDTO);

        assertNotNull(result);
        assertEquals(result.getPrice(), updateStockDTO.price());

        verify(stockRepository).save(stock);
    }

    @Test
    void shouldNotUpdatePriceIfNull() {
        UpdateStockDTO newUpdateStock = new UpdateStockDTO(
                null
        );

        given(findService.findProductByStoreAndId(store.getId(), product.getId())).willReturn(product);
        given(stockRepository.save(stock)).willReturn(stock);

        Stock result = stockService.updatePrice(store.getId(), product.getId(), newUpdateStock);

        assertNotNull(result);
        assertEquals(result.getPrice(), stock.getPrice());

        verify(stockRepository).save(stock);
    }
}