package br.com.pulsar.products.domain.services.stock.impl;

import br.com.pulsar.products.domain.dtos.stock.CreateStockDTO;
import br.com.pulsar.products.domain.dtos.stock.UpdateStockDTO;
import br.com.pulsar.products.domain.mappers.StockMapper;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Stock;
import br.com.pulsar.products.domain.repositories.StockRepository;
import br.com.pulsar.products.domain.services.find.FindService;
import br.com.pulsar.products.domain.services.stock.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
