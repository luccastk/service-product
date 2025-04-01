package br.com.pulsar.products.domain.entities;

import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StockId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import org.springframework.cglib.core.Local;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Batch {

    private final BatchId id;
    private Integer quantity;
    private LocalDate validity;
    private final LocalDateTime createTime;

    public Batch(Integer quantity, LocalDate validity){

        if ( quantity <= 0) {
            throw new IllegalArgumentException("Quantidade não pode ser inferio a 0.");
        }

        if ( validity.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Validade fora do intervalo");
        }

        this.id = new BatchId();
        this.quantity = quantity;
        this.validity = validity;
        this.createTime = LocalDateTime.now();
    }

    public Batch(BatchId id, Integer quantity, LocalDate validity, LocalDateTime createTime) {
        this.id = id;
        this.quantity = quantity;
        this.validity = validity;
        this.createTime = createTime;
    }

    public void updateBatch(Integer quantity, LocalDate validity){
        if (quantity != null) {
            this.quantity = quantity;
        }

        if (validity != null) {
            this.validity = validity;
        }
    }

    public void reduceQuantity(int amount){
        if ( amount > this.quantity) {
            throw new IllegalArgumentException("Amount is greater than quantity.");
        }
        this.quantity -= amount;
    }

    public boolean isDepleted() {
        return this.quantity <= 0;
    }

    public boolean isExpiring() {
        return LocalDate.now().isAfter(validity);
    }

    public boolean expiringSoon(Long plusMouth){
        return validity.isBefore(LocalDate.now().plusMonths(plusMouth));
    }

    public BatchId getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDate getValidity() {
        return validity;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }
}
