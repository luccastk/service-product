package br.com.pulsar.products.domain.entities;

import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StockId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import org.springframework.cglib.core.Local;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Product {

    private final ProductId id;
    private final StoreId storeId;
    private String name;
    private boolean active;
    private final Stock stock;

    public Product(StoreId storeId, String name, BigDecimal price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty.");
        }

        if (storeId == null) {
            throw new IllegalArgumentException("Loja não encontrada!");
        }

        if (price == null) {
            throw new IllegalArgumentException("O preço está nulo.");
        }

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço menor que zero.");
        }

        this.id = new ProductId();
        this.storeId = storeId;
        this.name = validateName(name);
        this.active = true;
        this.stock = new Stock(price);
    }

    public Product(ProductId id, StoreId storeId, String name, boolean active, Stock stock) {
        this.id = id;
        this.storeId = storeId;
        this.name = name;
        this.active = active;
        this.stock = stock;
    }

    public List<Batch> getBatches(){
        return this.stock.getBatches();
    }

    public int getTotalQuantity() {
        return this.stock.getTotalQuantity();
    }

    public BigDecimal getDefaultPrice() {
        return this.stock.getPrice();
    }

    public List<Batch> getValiditiesByMonths(Long plusMonths){
        return this.stock.verifyValidity(plusMonths);
    }

    public Optional<Batch> getLastAddBatch(){
        return this.stock.getLastAddBatch();
    }

    public Batch findBatchById(BatchId batchId){
        return this.stock.findById(batchId);
    }

    public void updateBatchById(BatchId batchId, int quantity, LocalDate validity){
        this.stock.updateBatch(batchId, quantity, validity);
    }

    public void updatePrice(BigDecimal price) {
        this.stock.updatePrice(price);
    }

    public void addBatches(int quantity, LocalDate validity) {
        this.stock.addBatch(quantity, validity);
    }

    public void rename(String newName){
        this.name = validateName(newName);
    }

    public void activate(){
        this.active = true;
    }

    public void deActivate(){
        this.active = false;
    }

    public StoreId getStoreId() {
        return storeId;
    }

    public Stock getStock() {
        return stock;
    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    private String validateName(String name) {
        Assert.hasText(name, "Name must not be null or empty.");
        return name.trim();
    }
}
