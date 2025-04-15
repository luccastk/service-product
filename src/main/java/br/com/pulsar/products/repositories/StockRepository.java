package br.com.pulsar.products.repositories;

import br.com.pulsar.products.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
