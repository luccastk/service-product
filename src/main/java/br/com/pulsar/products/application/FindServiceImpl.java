package br.com.pulsar.products.application;

import br.com.pulsar.products.domain.entities.Product;
import br.com.pulsar.products.domain.entities.Stock;
import br.com.pulsar.products.domain.entities.Store;
import br.com.pulsar.products.domain.repositories.ProductRepository;
import br.com.pulsar.products.domain.repositories.StoreRepository;
import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StockId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class FindServiceImpl implements FindService{

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final MessageSource m;

    @Override
    public Product findByStoreId(StoreId storeId) {
        return productRepository.findByStoreId(storeId)
                .orElseThrow(() -> new EntityNotFoundException(m.getMessage("PROD-008", null, Locale.getDefault())));
    }

    @Override
    public Product findByProductId(ProductId productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(m.getMessage("PROD-008", null, Locale.getDefault())));
    }

    @Override
    public Product findByProductIdAndStoreId(ProductId productId, StoreId storeId) {
        return productRepository.findByProductIdAndStoreId(productId, storeId)
                .orElseThrow(() -> new EntityNotFoundException(m.getMessage("PROD-008", null, Locale.getDefault())));
    }

    @Override
    public Store getStoreId(StoreId storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotFoundException(m.getMessage("STORE-001", null, Locale.getDefault())));
    }
}
