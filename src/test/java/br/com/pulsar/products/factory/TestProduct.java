package br.com.pulsar.products.factory;

import br.com.pulsar.products.dtos.http.ResponseProductDTO;
import br.com.pulsar.products.dtos.http.ResponseWrapperProductDTO;
import br.com.pulsar.products.dtos.products.CreateProductDTO;
import br.com.pulsar.products.dtos.products.UpdateProductDTO;
import br.com.pulsar.products.models.Product;

import java.util.List;
import java.util.UUID;

import static br.com.pulsar.products.factory.TestBatch.createBatchDTO;
import static br.com.pulsar.products.factory.TestStock.*;
import static br.com.pulsar.products.factory.TestStore.createValidStore;

public class TestProduct {

    private static final String NAME_FOR_PRODUCT = "product test";
    private static final String NEW_NAME_FOR_PRODUCT = "product test";

    public static Product createProduct() {
        Product p = new Product();
        p.setId(UUID.randomUUID());
        p.setName(NAME_FOR_PRODUCT);
        p.setStore(createValidStore());
        p.setStock(stock());
        return p;
    }

    public static Product updatedProduct() {
        Product p = new Product();
        p.setId(UUID.randomUUID());
        p.setName(NEW_NAME_FOR_PRODUCT);
        p.setStore(createValidStore());
        return p;
    }

    public static CreateProductDTO createProductDTO() {
        return new CreateProductDTO(
                NAME_FOR_PRODUCT,
                createStockDTO(),
                List.of(createBatchDTO())
        );
    }

    public static UpdateProductDTO updateProductDTO() {
        return new UpdateProductDTO(
                NAME_FOR_PRODUCT,
                true
        );
    }

    public static ResponseWrapperProductDTO responseWrapperProductDTOWithCursorNull() {
        return new ResponseWrapperProductDTO(
                List.of(responseProductDTO()),
                null
        );
    }

    public static ResponseWrapperProductDTO responseWrapperProductDTOWithCursorTest() {
        return new ResponseWrapperProductDTO(
                List.of(responseProductDTO()),
                responseProductDTO().name()
        );
    }

    public static ResponseProductDTO responseProductDTO() {
        return new ResponseProductDTO(
                UUID.randomUUID(),
                createProductDTO().name(),
                true,
                stockDTO()
        );
    }

    public static ResponseProductDTO updateResponseProductDTO() {
        return new ResponseProductDTO(
                UUID.randomUUID(),
                updateProductDTO().name(),
                true,
                stockDTO()
        );
    }
}
