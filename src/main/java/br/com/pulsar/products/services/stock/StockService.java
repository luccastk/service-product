package br.com.pulsar.products.services.stock;

import br.com.pulsar.products.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.dtos.stock.UpdateStockDTO;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Stock;

import java.util.UUID;

public interface StockService {
    Stock createStockForProduct(Product product, CreateStockDTO json);

    Stock updatePrice(UUID storeId, UUID productId, UpdateStockDTO json);

    void updateStockQuantity(Stock stock, Integer quantity);
}
