package br.com.pulsar.products.services.stock.impl;

import br.com.pulsar.products.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.dtos.products.CreateProductDTO;
import br.com.pulsar.products.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.dtos.stock.UpdateStockDTO;
import br.com.pulsar.products.mappers.StockMapper;
import br.com.pulsar.products.models.Batch;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Stock;
import br.com.pulsar.products.repositories.StockRepository;
import br.com.pulsar.products.services.find.FindService;
import br.com.pulsar.products.services.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final FindService find;

    @Override
    public Stock createStockForProduct(Product product, CreateStockDTO json) {
        Stock stock = stockMapper.toEntity(json);
        stock.setProduct(product);
        stock.setStore(product.getStore());
        return stock;
    }

    @Override
    public void updateStockQuantity(Stock stock, Integer newQuantity) {
        if (stock.getQuantity() == null) {
            stock.setQuantity(newQuantity);
        } else {
            stock.setQuantity(stock.getQuantity() + newQuantity);
        }
        stockRepository.save(stock);
    }

    @Override
    public Stock updatePrice(UUID storeId, UUID productId, UpdateStockDTO json) {
        Product product = find.findProductByStoreAndId(storeId, productId);
        Stock stock = product.getStock();

        if (json.price() != null) {
            stock.setPrice(json.price());
        }

        return stockRepository.save(stock);
    }
}
