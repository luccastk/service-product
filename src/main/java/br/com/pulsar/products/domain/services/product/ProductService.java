package br.com.pulsar.products.domain.services.product;

import br.com.pulsar.products.domain.dtos.http.ResponseWrapperProductDTO;
import br.com.pulsar.products.domain.dtos.products.CreateProductDTO;
import br.com.pulsar.products.domain.dtos.products.UpdateProductDTO;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Store;

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
