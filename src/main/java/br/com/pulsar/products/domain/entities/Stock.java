package br.com.pulsar.products.domain.entities;

import br.com.pulsar.products.domain.exceptions.BusinessRuleViolation;
import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.StockId;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Stock {

    private BigDecimal price;
    private final List<Batch> batches;

    public Stock() {
        this.batches = new ArrayList<>();
    }

    public Stock(BigDecimal price) {

        if (price == null) {
            throw  new IllegalArgumentException("Preço nullo");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço não pode ser menor que zero");
        }

        this.price = price;
        this.batches = new ArrayList<>();
    }

    public Stock(BigDecimal price, List<Batch> batches) {
        this.price = price;
        this.batches = batches;
    }

    public void addBatch(int quantity, LocalDate validity){
        validateBatch(quantity, validity);
        this.batches.add(new Batch(quantity, validity));
    }

    private void validateBatch(int quantity, LocalDate validity) {
        if (validity == null || validity.isBefore(LocalDate.now())) {
            throw new BusinessRuleViolation("Validade inválida");
        }
        if (quantity <= 0) {
            throw new BusinessRuleViolation("Quantidade inválida");
        }
        if (price == null) {
            throw new BusinessRuleViolation("Preço inválido");
        }
    }

    private void cleanDepletedBatches() {
        batches.removeIf(Batch::isDepleted);
    }

    public void updatePrice(BigDecimal newPrice) {
        this.price = newPrice;
    }

    public Batch findById(BatchId batchId) {
        return batches.stream()
                .filter(batch -> batch.getId().equals(batchId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Batch not found."));
    }

    public void updateBatch(BatchId batchId, int quantity, LocalDate validity) {
        this.batches.stream()
                .filter(i -> i.getId().equals(batchId))
                .findFirst()
                .ifPresent(
                        i -> i.updateBatch(quantity, validity)
                );
    }

    public List<LocalDateTime> getTimeStamps() {
        return batches.stream()
                .map(Batch::getCreateTime)
                .toList();
    }

    public List<LocalDate> getValidities(){
        return batches.stream()
                .map(Batch::getValidity)
                .toList();
    }

    public Optional<Batch> getLastAddBatch(){
        if (batches.isEmpty()) return Optional.empty();
        return Optional.of(batches.get(batches.size() - 1));
    }

    public int getTotalQuantity() {
        return batches.stream()
                .mapToInt(Batch::getQuantity)
                .sum();
    }

    public void removeQuantityByFefo(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be higher than 0.");
        }

        int remaining = amount;
        List<Batch> sortedBatches = new ArrayList<>(batches);
        sortedBatches.sort(Comparator.comparing(batch -> !batch.isExpiring()));

        for (Batch batch : sortedBatches) {
            if (remaining <= 0) break;

            if (batch.getQuantity() > 0) {
                int min = Math.min(batch.getQuantity(), remaining);
                batch.reduceQuantity(min);
                remaining -= min;
            }
        }

        cleanDepletedBatches();

        if (remaining > 0) {
            throw new IllegalArgumentException("Not enough stock to remove the requested amount.");
        }
    }

    public List<Batch> verifyValidity(Long plusMonths) {
        if (plusMonths < 0) {
            throw new IllegalArgumentException("plusMonths must be non-negative.");
        }

        return this.batches.stream()
                .filter(batch -> batch.expiringSoon(plusMonths))
                .toList();
    }

    public BigDecimal applyDiscount(BigDecimal discountPercentage) {
        if (discountPercentage.compareTo(BigDecimal.ZERO) < 0 || discountPercentage.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 1.");
        }

        return price.multiply(BigDecimal.ONE.subtract(discountPercentage)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<Batch> getBatches() {
        return List.copyOf(batches);
    }
}
