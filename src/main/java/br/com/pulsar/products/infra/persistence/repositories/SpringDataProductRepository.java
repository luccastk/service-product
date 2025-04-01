package br.com.pulsar.products.infra.persistence.repositories;

import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import br.com.pulsar.products.infra.persistence.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.id = :uuid")
    Optional<ProductEntity> findByUuid(ProductId uuid);

    Optional<ProductEntity> findByStore(StoreId storeId);

    Boolean existsByNameIgnoringCase(String name);

    List<ProductEntity> findAllByActiveTrue();

    @Query("SELECT p FROM ProductEntity p WHERE p.name IN :names")
    List<ProductEntity> findByNameIn(List<String> names);

    @Query("""
            SELECT p FROM ProductEntity p
            WHERE p.store = :storeId AND p.active = true
            ORDER BY p.name ASC
            LIMIT :limit
            """)
    List<ProductEntity> findProductPerStore(StoreId storeId, int limit);

    @Query("""
            SELECT p FROM ProductEntity p
            WHERE p.store = :storeId
            AND (:cursor IS NULL OR p.name > :cursor)
            AND p.active = true
            ORDER BY p.name ASC
            LIMIT :limit
            """)
    List<ProductEntity> findProductPerStoreAfterCursor(StoreId storeId, String cursor, int limit);

    @Query("""
            SELECT p FROM ProductEntity p
            WHERE p.id = :productId
            AND p.store = :storeId
            """)
    Optional<ProductEntity> findByProductIdAndStore(ProductId productId, StoreId storeId);

    boolean existsById(ProductId id);
}
