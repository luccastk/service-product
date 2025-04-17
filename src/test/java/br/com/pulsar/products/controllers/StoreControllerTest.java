package br.com.pulsar.products.controllers;

import br.com.pulsar.products.domain.services.rest.ApiStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApiStore apiStore;

    @Test
    void createStore() {
    }

    @Test
    void shouldErrorNotFoundForCreateProduct() throws Exception {

        String json = "{}";

        var response = mockMvc.perform(
                post("/v1/stores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void getStores() {

    }

    @Test
    void updateStore() {
    }

    @Test
    void storeDetail() {
    }

    @Test
    void deleteStore() {
    }
}