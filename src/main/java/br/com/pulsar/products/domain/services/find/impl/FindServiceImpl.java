package br.com.pulsar.products.domain.services.find.impl;

import br.com.pulsar.products.domain.models.Batch;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.repositories.BatchRepository;
import br.com.pulsar.products.domain.repositories.ProductRepository;
import br.com.pulsar.products.domain.repositories.StoreRepository;
import br.com.pulsar.products.domain.services.find.FindService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindServiceImpl implements FindService {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final BatchRepository batchRepository;
    private final MessageSource m;

    @Override
    public Product findProductByStoreAndId(UUID storeId, UUID productId) {
        return productRepository.findByStore_IdAndId(storeId,productId)
                .orElseThrow(() -> new EntityNotFoundException(m.getMessage("PROD-008", null, Locale.getDefault())));
    }

    @Override
    public Store findStoreId(UUID storeId) {
        return storeRepository.findByUuid(storeId)
                .orElseThrow(() -> new EntityNotFoundException(m.getMessage("STORE-001", null, Locale.getDefault())));    }

    @Override
    public Batch findByStoreAndBatchId(UUID storeId, UUID batchId) {
        return batchRepository.findByStock_Product_Store_IdAndId(storeId, batchId)
                .orElseThrow(() -> new EntityNotFoundException(m.getMessage("BATCH-006", null, Locale.getDefault())));
    }
}
