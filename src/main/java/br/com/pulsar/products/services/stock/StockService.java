package br.com.pulsar.products.services.stock;

import br.com.pulsar.products.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.dtos.products.CreateProductDTO;
import br.com.pulsar.products.dtos.stock.UpdateStockDTO;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Stock;

import java.util.List;
import java.util.UUID;

public interface StockService {
    Stock createStockForProduct(Product product, CreateProductDTO json);

    Integer sumQuantities(List<CreateBatchDTO> json);

    Stock updatePrice(UUID storeId, UUID productId, UpdateStockDTO json);

    void updateStockQuantity(Stock stock, Integer quantity);
}
