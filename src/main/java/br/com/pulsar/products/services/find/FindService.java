package br.com.pulsar.products.services.find;

import br.com.pulsar.products.models.Batch;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Store;

import java.util.UUID;

public interface FindService {

    Product findProductByStoreAndId(UUID storeId, UUID productId);

    Store findStoreId(UUID storeId);

    Batch findByStoreAndBatchId(UUID storeId, UUID batchId);
}
