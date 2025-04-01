package br.com.pulsar.products.infra.persistence.adapters;

import br.com.pulsar.products.application.mappers.BatchMapper;
import br.com.pulsar.products.application.mappers.ProductMapper;
import br.com.pulsar.products.domain.entities.Batch;
import br.com.pulsar.products.domain.entities.Product;
import br.com.pulsar.products.domain.repositories.ProductRepository;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import br.com.pulsar.products.infra.persistence.entities.ProductEntity;
import br.com.pulsar.products.infra.persistence.repositories.SpringDataProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final SpringDataProductRepository springDataRepository;
    private final ProductMapper productMapper;
    private final BatchMapper batchMapper;

    public ProductRepositoryImpl(SpringDataProductRepository springDataRepository, ProductMapper productMapper, BatchMapper batchMapper) {
        this.springDataRepository = springDataRepository;
        this.productMapper = productMapper;
        this.batchMapper = batchMapper;
    }

    @Override
    public Product save(Product product) {

        ProductEntity productEntity;

        if (product.getId() != null && springDataRepository.existsById(product.getId())) {
            productEntity = springDataRepository.findByUuid(product.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found."));


            productEntity.setName(product.getName());
            productEntity.setActive(product.isActive());
            productEntity.setDefaultPrice(product.getDefaultPrice());

            productEntity.addBatch();
        } else {
            productEntity = productMapper.toEntity(product);
            productEntity.getBatches().forEach(batch -> batch.setProduct(productEntity));
        }

        springDataRepository.save(productEntity);
        return productMapper.toDomain(productEntity);
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return springDataRepository.findByUuid(id)
                .map(productMapper::toDomain);
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return productMapper.toDomain(springDataRepository.saveAll(productMapper.toEntity(products)));
    }

    @Override
    public List<Product> findProductPerStore(StoreId storeId, int limit) {
        return springDataRepository.findProductPerStore(storeId, limit)
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }

    @Override
    public List<Product> findProductPerStoreAfterCursor(StoreId storeId, String cursos, int limit) {
        return springDataRepository.findProductPerStoreAfterCursor(storeId, cursos, limit)
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByNameIgnoringCase(String name) {
        return springDataRepository.existsByNameIgnoringCase(name);
    }

    @Override
    public List<Product> findAllByActiveTrue() {
        return springDataRepository.findAllByActiveTrue()
                .stream()
                .map(productMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Product> findByStoreId(StoreId storeId) {
        return springDataRepository.findByStore(storeId)
                .map(productMapper::toDomain);
    }

    @Override
    public Optional<Product> findByProductIdAndStoreId(ProductId productId, StoreId storeId) {
        return springDataRepository.findByProductIdAndStore(productId, storeId)
                .map(productMapper::toDomain);
    }
}
