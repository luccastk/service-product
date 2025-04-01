package br.com.pulsar.products.infra.persistence.repositories;

import br.com.pulsar.products.domain.valueobjects.StoreId;
import br.com.pulsar.products.infra.persistence.entities.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataStoreRepository extends JpaRepository<StoreEntity, Long> {

    @Query("SELECT s FROM StoreEntity s WHERE s.id = :uuid")
    List<StoreEntity> findAllByUuid(List<StoreId> uuid);

    @Query("SELECT s FROM StoreEntity s WHERE s.id = :uuid")
    Optional<StoreEntity> findByUuid(StoreId uuid);

    List<StoreEntity> findAllByActiveTrue();

    Boolean existsByNameIgnoringCase(String name);
}
