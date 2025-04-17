package br.com.pulsar.products.controllers;

import br.com.pulsar.products.domain.dtos.batch.BatchWrapperDTO;
import br.com.pulsar.products.domain.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.domain.dtos.batch.UpdateBatchDTO;
import br.com.pulsar.products.domain.models.Batch;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.services.rest.ApiBatch;
import br.com.pulsar.products.factory.TestBatch;
import br.com.pulsar.products.factory.TestProduct;
import br.com.pulsar.products.factory.TestStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class BatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ApiBatch apiBatch;

    private BatchWrapperDTO batchWrapperDTO;
    private Store store;
    private Product product;
    private Batch batch;
    private UpdateBatchDTO updateBatchDTO;

    @BeforeEach
    void setUp() {
        store = TestStore.createStore();
        product = TestProduct.createProduct();
        batch = TestBatch.createBatch();

        CreateBatchDTO createBatchDTO = TestBatch.createBatchDTO();

        batchWrapperDTO = new BatchWrapperDTO(
                List.of(createBatchDTO)
        );

        updateBatchDTO = TestBatch.updateBatchDTO();
    }

    @Test
    void shouldCreateBatch() throws Exception {
        String json = objectMapper.writeValueAsString(batchWrapperDTO);

        MockHttpServletResponse response = mockMvc.perform(
                post("/v1/stores/{storeId}/batches/{productId}", store.getId(), product.getId())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldGetBatchMovements() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/v1/stores/{storeId}/product/{productId}", store.getId(), product.getId())
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldGetSingleBatch() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/v1/stores/{storeId}/batches/{batchId}", store.getId(), batch.getId())
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldUpdateBatchAndProduct() throws Exception {
        String json = objectMapper.writeValueAsString(updateBatchDTO);

        MockHttpServletResponse response = mockMvc.perform(
                put("/v1/stores/{storeId}/batches/{batchId}", store.getId(), batch.getId())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }
}