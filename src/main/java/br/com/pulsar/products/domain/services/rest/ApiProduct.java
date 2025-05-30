package br.com.pulsar.products.domain.services.rest;

import br.com.pulsar.products.domain.dtos.http.ResponseProductDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseWrapperProductDTO;
import br.com.pulsar.products.domain.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.domain.dtos.products.UpdateProductDTO;

import java.util.List;
import java.util.UUID;

public interface ApiProduct {
    List<ResponseProductDTO> createProduct(UUID storeId, ProductWrapperDTO json);
    ResponseProductDTO getProductById(UUID storeId, UUID productId);
    ResponseWrapperProductDTO listPageProductsActive(UUID storeId, String cursor, int limit);
    ResponseProductDTO updateProduct(UUID storeId, UUID productId, UpdateProductDTO json);
    void deActivateProduct(UUID storeId, UUID productId);
}
