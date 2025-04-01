package br.com.pulsar.products.application.stock.impl;

import br.com.pulsar.products.application.FindService;
import br.com.pulsar.products.application.mappers.ProductMapper;
import br.com.pulsar.products.application.product.dtos.ResponseProductDTO;
import br.com.pulsar.products.application.stock.StockService;
import br.com.pulsar.products.application.stock.dtos.UpdateStockPriceDTO;
import br.com.pulsar.products.domain.entities.Product;
import br.com.pulsar.products.domain.entities.Stock;
import br.com.pulsar.products.domain.repositories.ProductRepository;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final ProductRepository productRepository;
    private final FindService findService;
    private final ProductMapper productMapper;

    @Override
    public ResponseProductDTO updateStockPrice(StoreId storeId, ProductId productId, UpdateStockPriceDTO json) {
        Product product = findService.findByProductIdAndStoreId(productId, storeId);
        product.updatePrice(json.price());
        productRepository.save(product);
        return productMapper.ToDTO(product);
    }
}
