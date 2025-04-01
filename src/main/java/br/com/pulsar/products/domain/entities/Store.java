package br.com.pulsar.products.domain.entities;

import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class Store {

    private final StoreId id;
    private String name;
    private boolean active;
    private final List<ProductId> productIds;

    public Store(String name) {
        if ( name == null || name.trim().isEmpty() ) {
            throw new IllegalArgumentException("Name must not be null or empty.");
        }
        this.id = new StoreId();
        this.name = name;
        this.active = true;
        this.productIds = new ArrayList<>();
    }

    public Store(String name, Boolean active, List<ProductId> productsIds) {
        if ( name == null || name.trim().isEmpty() ) {
            throw new IllegalArgumentException("Name must not be null or empty.");
        }

        this.id = new StoreId();
        this.name = validateName(name);
        this.active = (active != null) ? active : true;
        this.productIds = (productsIds != null) ? new ArrayList<>(productsIds) : new ArrayList<>();
     }

    public Store(StoreId id, String name, boolean active, List<ProductId> productIds) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.productIds = productIds;
    }

    private String validateName(String name){
        Assert.hasText(name,"Name must not be null or empty.");
        return name.trim();
    }

    public void addProduct(ProductId productId) {
        this.productIds.add(productId);
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

    public boolean isActive() {
        return active;
    }

    public StoreId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProductId> getProductIds() {
        return productIds;
    }
}
