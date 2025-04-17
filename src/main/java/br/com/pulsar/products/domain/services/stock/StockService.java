package br.com.pulsar.products.domain.services.stock;

import br.com.pulsar.products.domain.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.domain.dtos.stock.UpdateStockDTO;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Stock;

import java.util.UUID;

public interface StockService {
    Stock createStockForProduct(Product product, CreateStockDTO json);

    Stock updatePrice(UUID storeId, UUID productId, UpdateStockDTO json);

    void updateStockQuantity(Stock stock, Integer quantity);
}
