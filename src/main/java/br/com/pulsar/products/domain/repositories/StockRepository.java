package br.com.pulsar.products.domain.repositories;

import br.com.pulsar.products.domain.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
