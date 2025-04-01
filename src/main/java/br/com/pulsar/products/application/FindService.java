package br.com.pulsar.products.application;

import br.com.pulsar.products.domain.entities.Product;
import br.com.pulsar.products.domain.entities.Stock;
import br.com.pulsar.products.domain.entities.Store;
import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StockId;
import br.com.pulsar.products.domain.valueobjects.StoreId;

public interface FindService {

    Product findByStoreId(StoreId storeId);
    Product findByProductId(ProductId productId);
    Product findByProductIdAndStoreId(ProductId productId, StoreId storeId);
    Store getStoreId(StoreId storeId);
}
