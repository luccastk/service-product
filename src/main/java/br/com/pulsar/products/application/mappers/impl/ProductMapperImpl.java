package br.com.pulsar.products.application.mappers.impl;

import br.com.pulsar.products.application.mappers.BatchMapper;
import br.com.pulsar.products.application.mappers.ProductMapper;
import br.com.pulsar.products.application.mappers.StoreMapper;
import br.com.pulsar.products.application.product.dtos.ResponseProductDTO;
import br.com.pulsar.products.domain.entities.Batch;
import br.com.pulsar.products.domain.entities.Product;
import br.com.pulsar.products.domain.entities.Stock;
import br.com.pulsar.products.domain.entities.Store;
import br.com.pulsar.products.infra.persistence.entities.BatchEntity;
import br.com.pulsar.products.infra.persistence.entities.ProductEntity;
import br.com.pulsar.products.infra.persistence.entities.StoreEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {

    private final BatchMapper batchMapper;

    @Override
    public ProductEntity toEntity(Product product) {
        if (product == null) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId(product.getId());
        productEntity.setDefaultPrice(product.getDefaultPrice());
        productEntity.setName(product.getName());
        productEntity.setActive(product.isActive());
        productEntity.setStore(product.getStoreId());

        List<BatchEntity> batchEntities = batchMapper.toEntity(product.getBatches(), productEntity);

        productEntity.setBatches(batchEntities);

        return productEntity;
    }

    @Override
    public List<ProductEntity> toEntity(List<Product> products) {
        return products == null ? List.of() : products.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public ResponseProductDTO ToDTO(Product product) {
        if (product == null) {
            return null;
        }

        return new ResponseProductDTO(
                product.getId().id(),
                product.getName(),
                product.isActive(),
                product.getTotalQuantity(),
                product.getDefaultPrice()
        );
    }

    @Override
    public List<ResponseProductDTO> ToDTO(List<Product> products) {
        return products == null ? List.of() : products.stream().map(this::ToDTO).collect(Collectors.toList());
    }

    @Override
    public Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        Stock stock = new Stock(entity.getDefaultPrice(), batchMapper.toDomain(entity.getBatches()));

        return new Product(
                entity.getId(),
                entity.getStore(),
                entity.getName(),
                entity.getActive(),
                stock
        );
    }

    @Override
    public List<Product> toDomain(List<ProductEntity> entity) {
        return entity == null ? List.of() : entity.stream().map(this::toDomain).collect(Collectors.toList());
    }

    private BatchEntity toEntityBatch(Batch batch, Product product) {
        if (batch == null) {
            return null;
        }

        BatchEntity batchEntity = new BatchEntity();

        batchEntity.setId(batch.getId());
        batchEntity.setQuantity(batch.getQuantity());
        batchEntity.setValidity(batch.getValidity());
        batchEntity.setCreateTime(batch.getCreateTime());
        batchEntity.setProduct(toEntity(product));

        return batchEntity;
    }

    private List<BatchEntity> toEntityBatch(List<Batch> batches, Product products) {
        if (batches.isEmpty()) {
            return null;
        }

        return batches.stream()
                .map(i -> toEntityBatch(i, products))
                .toList();
    }
}
