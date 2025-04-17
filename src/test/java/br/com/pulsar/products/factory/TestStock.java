package br.com.pulsar.products.factory;

import br.com.pulsar.products.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.dtos.stock.StockDTO;
import br.com.pulsar.products.models.Stock;

import java.math.BigDecimal;
import java.util.List;

import static br.com.pulsar.products.factory.TestBatch.batch;

public class TestStock {

    private final static BigDecimal PRICE_STOCK = BigDecimal.valueOf(100);
    private final static Integer QUANTITY_STOCK = 100;


    public static Stock stock() {
        Stock s = new Stock();
        s.setQuantity(QUANTITY_STOCK);
        s.setBatches(List.of(batch()));
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
}
