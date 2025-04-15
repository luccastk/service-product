package br.com.pulsar.products.services.rest.impl;

import br.com.pulsar.products.dtos.http.ResponseProductDTO;
import br.com.pulsar.products.dtos.http.ResponseWrapperProductDTO;
import br.com.pulsar.products.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.dtos.products.UpdateProductDTO;
import br.com.pulsar.products.mappers.ProductMapper;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Store;
import br.com.pulsar.products.services.find.FindService;
import br.com.pulsar.products.services.product.ProductService;
import br.com.pulsar.products.services.rest.ApiProduct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ApiProductImpl implements ApiProduct {

    private final ProductService productService;
    private final FindService find;
    private final ProductMapper productMapper;

    public ApiProductImpl(ProductService productService, ProductMapper productMapper, FindService find) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.find = find;
    }

    @Override
    public List<ResponseProductDTO> createProduct(UUID storeId, ProductWrapperDTO json) {
        Store store = find.findStoreId(storeId);
        List<Product> products = json.products().stream()
                .map(mapping -> productService.saveProducts(store, mapping))
                .toList();
        return productMapper.ToDTO(
                products
        );
    }

    @Override
    public ResponseProductDTO getProductById(UUID storeId, UUID productId) {
        return productMapper.ToDTO(
                productService.productDetail(storeId, productId)
        );
    }

    @Override
    public ResponseProductDTO listProductsByStoreId(UUID storeId) {
        return null;
    }

    @Override
    public ResponseWrapperProductDTO listPageProductsActive(UUID storeId, String cursor, int limit) {
        return productService.listProductPerStore(storeId, cursor, limit);
    }

    @Override
    public ResponseProductDTO updateProduct(UUID storeId, UUID productId, UpdateProductDTO json) {
        return productMapper.ToDTO(
                productService.updateProduct(storeId, productId, json)
        );
    }

    @Override
    public void deActivateProduct(UUID storeId, UUID productId) {
        productService.deActiveProduct(storeId, productId);
    }
}
