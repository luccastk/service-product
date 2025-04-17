package br.com.pulsar.products.services.rest.impl;

import br.com.pulsar.products.domain.dtos.http.ResponseProductDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseWrapperProductDTO;
import br.com.pulsar.products.domain.dtos.products.CreateProductDTO;
import br.com.pulsar.products.domain.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.domain.dtos.products.UpdateProductDTO;
import br.com.pulsar.products.domain.services.rest.impl.ApiProductImpl;
import br.com.pulsar.products.factory.TestProduct;
import br.com.pulsar.products.factory.TestStore;
import br.com.pulsar.products.domain.mappers.ProductMapper;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.services.find.FindService;
import br.com.pulsar.products.domain.services.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
class ApiProductImplTest {

    @InjectMocks
    private ApiProductImpl restProduct;

    @Mock
    private FindService findService;

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    private Store store;
    private Product product;
    private CreateProductDTO createProductDTO;
    private ProductWrapperDTO productWrapperDTO;
    private ResponseProductDTO responseProductDTO;
    private ResponseWrapperProductDTO responseWrapperProductDTO;
    private UpdateProductDTO updateProductDTO;

    @BeforeEach
    void setUp() {
        store = TestStore.createStore();

        product = TestProduct.createProduct();

        productWrapperDTO = new ProductWrapperDTO(
                List.of(TestProduct.createProductDTO())
        );

        createProductDTO = TestProduct.createProductDTO();

        responseProductDTO = TestProduct.responseProductDTO();
        responseWrapperProductDTO = TestProduct.responseWrapperProductDTOWithCursorNull();

        updateProductDTO = TestProduct.updateProductDTO();
    }

    @Test
    void shouldCreateProduct() {
        given(findService.findStoreId(store.getId())).willReturn(store);
        given(productService.saveProducts(store, createProductDTO)).willReturn(product);
        given(productMapper.ToDTO(List.of(product))).willReturn(List.of(responseProductDTO));

        List<ResponseProductDTO> result = restProduct.createProduct(store.getId(), productWrapperDTO);

        assertNotNull(result);
        assertEquals(result.get(0).name(), product.getName());
    }

    @Test
    void shouldGetProductById() {
        given(productService.productDetail(store.getId(), product.getId())).willReturn(product);
        given(productMapper.ToDTO(product)).willReturn(responseProductDTO);

        ResponseProductDTO result = restProduct.getProductById(store.getId(), product.getId());

        assertNotNull(result);
    }

    @Test
    void listPageProductsActiveWithCursor() {
        responseWrapperProductDTO = TestProduct.responseWrapperProductDTOWithCursorTest();

        given(productService.listProductPerStore(store.getId(), responseProductDTO.name(), 50)).willReturn(responseWrapperProductDTO);

        ResponseWrapperProductDTO result = restProduct.listPageProductsActive(store.getId(), responseProductDTO.name(), 50);

        assertNotNull(result);
        assertEquals(result.nextCursor(), responseProductDTO.name());
    }

    @Test
    void listPageProductsActiveWithoutCursor() {
        given(productService.listProductPerStore(store.getId(), null, 50)).willReturn(responseWrapperProductDTO);

        ResponseWrapperProductDTO result = restProduct.listPageProductsActive(store.getId(), null, 50);

        assertNotNull(result);
        assertNull(result.nextCursor());
    }

    @Test
    void updateProduct() {
        product = TestProduct.updatedProduct();
        responseProductDTO = TestProduct.updateResponseProductDTO();

        given(productService.updateProduct(store.getId(), product.getId(), updateProductDTO)).willReturn(product);
        given(productMapper.ToDTO(product)).willReturn(responseProductDTO);

        ResponseProductDTO result = restProduct.updateProduct(store.getId(), product.getId(), updateProductDTO);

        assertNotNull(result);
        assertEquals(result.name(), updateProductDTO.name());
    }

    @Test
    void deActivateProduct() {
        restProduct.deActivateProduct(store.getId(), product.getId());
        verify(productService).deActiveProduct(store.getId(), product.getId());
    }
}