package br.com.pulsar.products.application.product.impl;

import br.com.pulsar.products.application.FindService;
import br.com.pulsar.products.application.batch.StockOperationsService;
import br.com.pulsar.products.application.mappers.ProductMapper;
import br.com.pulsar.products.application.product.ProductService;
import br.com.pulsar.products.application.product.dtos.BulkCreateProductDTO;
import br.com.pulsar.products.application.product.dtos.ResponseProductDTO;
import br.com.pulsar.products.application.product.dtos.ResponseProductPageDTO;
import br.com.pulsar.products.application.product.dtos.UpdateProductDTO;
import br.com.pulsar.products.domain.entities.Product;
import br.com.pulsar.products.domain.repositories.ProductRepository;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final MessageSource m;
    private final ProductMapper productMapper;
    private final FindService findService;

    @Override
    public List<ResponseProductDTO> createProduct(StoreId storeId, BulkCreateProductDTO json) {
        List<Product> products = json.products().stream()
                .map(i -> {
                    Product product = new Product(storeId, i.name(), i.price());
                    product.addBatches(i.batches().quantity(), i.batches().validity());
                    return product;
                }).toList();
        return productMapper.ToDTO(repository.saveAll(products));
    }

    @Override
    public ResponseProductDTO updateProductName(StoreId storeId, ProductId productId, UpdateProductDTO json) {
        Product product = findService.findByProductIdAndStoreId(productId, storeId);
        product.rename(json.name());
        return productMapper.ToDTO(repository.save(product));
    }

    @Override
    public ResponseProductDTO activateProduct(StoreId storeId, ProductId productId) {
        Product product = findService.findByProductIdAndStoreId(productId, storeId);
        product.activate();
        return productMapper.ToDTO(repository.save(product));
    }

    @Override
    public ResponseProductDTO detailProduct(StoreId storeId, ProductId productId) {
        Product product = findService.findByProductIdAndStoreId(productId, storeId);
        return productMapper.ToDTO(product);
    }

    @Override
    public void deactivateProduct(StoreId storeId, ProductId productId) {
        Product product = findService.findByProductIdAndStoreId(productId, storeId);
        product.deActivate();
        repository.save(product);
    }

    @Transactional
    @Override
    public ResponseProductPageDTO listProductPerStore(StoreId storeId, String cursor, int limit) {
        List<Product> productList;

        if (cursor == null) {
            productList = repository.findProductPerStore(storeId, limit);
        } else {
            productList = repository.findProductPerStoreAfterCursor(storeId, cursor, limit);
        }

        String nextCursor = productList.size() == limit ? productList.get(productList.size() - 1).getName() : null;

        return new ResponseProductPageDTO(
                productMapper.ToDTO(productList),
                nextCursor
        );
    }
}
