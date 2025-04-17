package br.com.pulsar.products.domain.services.find;

import br.com.pulsar.products.domain.models.Batch;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Store;

import java.util.UUID;

public interface FindService {

    Product findProductByStoreAndId(UUID storeId, UUID productId);

    Store findStoreId(UUID storeId);

    Batch findByStoreAndBatchId(UUID storeId, UUID batchId);
}
