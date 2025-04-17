package br.com.pulsar.products.domain.repositories;

import br.com.pulsar.products.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Boolean existsByNameIgnoringCase(String name);

    @Query("""
            SELECT p FROM Product p
            WHERE p.store.id = :storeId AND p.active = true
            ORDER BY p.name ASC
            LIMIT :limit
            """)
    List<Product> findProductPerStore(UUID storeId, int limit);

    @Query("""
            SELECT p FROM Product p
            WHERE p.store.id = :storeId
            AND (:cursor IS NULL OR p.name > :cursor)
            AND p.active = true
            ORDER BY p.name ASC
            LIMIT :limit
            """)
    List<Product> findProductPerStoreAfterCursor(UUID storeId, String cursor, int limit);


    Optional<Product> findByStore_IdAndId(UUID store, UUID productId);
}
