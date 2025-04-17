package br.com.pulsar.products.services.product;

import br.com.pulsar.products.dtos.http.ResponseWrapperProductDTO;
import br.com.pulsar.products.dtos.products.*;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Store;

import java.io.InputStream;
import java.util.UUID;

public interface ProductService {

    Product saveProducts(Store store, CreateProductDTO json);

    Product updateProduct(UUID storeId, UUID productId, UpdateProductDTO json);

    Product productDetail(UUID storeId, UUID productId);

    void deActiveProduct(UUID storeId, UUID productId);

    ResponseWrapperProductDTO listProductPerStore(UUID storeId, String cursor, int limit);

    void convertCsvToEntity(UUID storeId, InputStream is);
}
