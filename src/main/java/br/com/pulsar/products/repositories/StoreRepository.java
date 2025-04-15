package br.com.pulsar.products.repositories;

import br.com.pulsar.products.models.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("SELECT s FROM Store s WHERE s.id = :uuid")
    Optional<Store> findByUuid(UUID uuid);

    List<Store> findAllByActiveTrue();

    Boolean existsByNameIgnoringCase(String name);
}
