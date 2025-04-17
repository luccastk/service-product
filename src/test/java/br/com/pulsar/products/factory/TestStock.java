package br.com.pulsar.products.factory;

import br.com.pulsar.products.domain.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.domain.dtos.stock.StockDTO;
import br.com.pulsar.products.domain.dtos.stock.UpdateStockDTO;
import br.com.pulsar.products.domain.models.Stock;

import java.math.BigDecimal;

import static br.com.pulsar.products.factory.TestBatch.createBatch;

public class TestStock {

    private final static BigDecimal PRICE_STOCK = BigDecimal.valueOf(100);
    private final static BigDecimal NEW_PRICE_STOCK = BigDecimal.valueOf(200);
    private final static Integer QUANTITY_STOCK = 100;
    private static final Long PK_FOR_STOCK = 1L;

    public static Stock createStock() {
        Stock s = new Stock();
        s.setPk(PK_FOR_STOCK);
        s.setPrice(PRICE_STOCK);
        s.setQuantity(createBatch().getQuantity());
        return s;
    }

    public static CreateStockDTO createStockDTO() {
        return new CreateStockDTO(
                PRICE_STOCK
        );
    }

    public static StockDTO stockDTO() {
        return new StockDTO(
                QUANTITY_STOCK,
                PRICE_STOCK
        );
    }

    public static UpdateStockDTO updateStockDTO() {
        return new UpdateStockDTO(
                NEW_PRICE_STOCK
        );
    }
}
