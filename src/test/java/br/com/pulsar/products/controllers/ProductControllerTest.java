package br.com.pulsar.products.controllers;

import br.com.pulsar.products.domain.dtos.products.CreateProductDTO;
import br.com.pulsar.products.domain.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.domain.dtos.products.UpdateProductDTO;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.services.rest.ApiProduct;
import br.com.pulsar.products.exceptions.DuplicationException;
import br.com.pulsar.products.factory.TestProduct;
import br.com.pulsar.products.factory.TestStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ApiProduct apiProduct;

    private ProductWrapperDTO productWrapperDTO;
    private UpdateProductDTO updateProductDTO;
    private Store store;
    private Product product;

    @BeforeEach
    void setUp() {
        store = TestStore.createStore();
        product = TestProduct.createProduct();
        CreateProductDTO createProductDTO = TestProduct.createProductDTO();

        productWrapperDTO = new ProductWrapperDTO(
                List.of(createProductDTO)
        );

        updateProductDTO = TestProduct.updateProductDTO();
    }

    @Test
    void shouldCreateProduct() throws Exception {
        String json = objectMapper.writeValueAsString(productWrapperDTO);
        MockHttpServletResponse response = mockMvc.perform(
                post("/v1/stores/{storeId}/products", store.getId())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldThrowDuplicationException() throws Exception {

        given(apiProduct.createProduct(store.getId(), productWrapperDTO)).willThrow(
                new DuplicationException("PROD-005")
        );

        String json = objectMapper.writeValueAsString(productWrapperDTO);
        MockHttpServletResponse response = mockMvc.perform(
                post("/v1/stores/{storeId}/products", store.getId())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void shouldGetProduct() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/v1/stores/{storeId}/products/{productId}", store.getId(), product.getId())
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        String json = objectMapper.writeValueAsString(updateProductDTO);

        MockHttpServletResponse response = mockMvc.perform(
                put("/v1/stores/{storeId}/products/{productId}", store.getId(), product.getId())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                delete("/v1/stores/{storeId}/products/{productId}", store.getId(), product.getId())
        ).andReturn().getResponse();

        assertEquals(204, response.getStatus());
    }

    @Test
    void shouldProductListStore() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/v1/stores/{storeId}/products", store.getId())
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }
}