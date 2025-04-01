package br.com.pulsar.products.domain.repositories;

import br.com.pulsar.products.domain.entities.Product;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(ProductId id);
    List<Product> saveAll(List<Product> products);
    List<Product> findProductPerStore(StoreId storeId, int limit);
    List<Product> findProductPerStoreAfterCursor(StoreId storeId, String cursos, int limit);
    boolean existsByNameIgnoringCase(String name);
    List<Product> findAllByActiveTrue();

    Optional<Product> findByStoreId(StoreId storeId);

    Optional<Product> findByProductIdAndStoreId(ProductId productId, StoreId storeId);
}
