package br.com.pulsar.products.repositories;

import br.com.pulsar.products.models.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    Optional<Batch> findByStock_Product_Store_IdAndId(UUID storeId, UUID batchId);

    @Query("""
            SELECT b FROM Batch b
            WHERE b.stock.product.active = true
            AND b.stock.product.store.id = :storeId
            AND b.validity BETWEEN :today AND :mouth
            ORDER BY b.stock.product.name
            """)
    List<Batch> findProductsExpiringSoon(
            @Param("storeId") UUID storeId,
            @Param("today") LocalDate today,
            @Param("mouth") LocalDate mouth
    );
}
