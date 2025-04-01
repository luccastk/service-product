package br.com.pulsar.products.application.product;

import br.com.pulsar.products.application.product.dtos.BulkCreateProductDTO;
import br.com.pulsar.products.application.product.dtos.ResponseProductDTO;
import br.com.pulsar.products.application.product.dtos.ResponseProductPageDTO;
import br.com.pulsar.products.application.product.dtos.UpdateProductDTO;
import br.com.pulsar.products.domain.entities.Product;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StockId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import br.com.pulsar.products.infra.persistence.entities.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ResponseProductDTO> createProduct(StoreId storeId, BulkCreateProductDTO json);
    ResponseProductDTO updateProductName(StoreId storeId, ProductId productId, UpdateProductDTO json);
    ResponseProductDTO activateProduct(StoreId storeId, ProductId productId);
    ResponseProductDTO detailProduct(StoreId storeId, ProductId productId);
    void deactivateProduct(StoreId storeId, ProductId productId);
    ResponseProductPageDTO listProductPerStore(StoreId storeId, String cursor, int limit);
}
