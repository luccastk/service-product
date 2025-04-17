package br.com.pulsar.products.controllers;

import br.com.pulsar.products.domain.dtos.http.ResponseStoreDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseWrapperStoreDTO;
import br.com.pulsar.products.domain.dtos.store.StoreWrapperDTO;
import br.com.pulsar.products.domain.dtos.store.UpdateStoreDTO;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.services.rest.ApiStore;
import br.com.pulsar.products.factory.TestStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ApiStore apiStore;

    private Store store;
    private StoreWrapperDTO storeWrapperDTO;
    private ResponseWrapperStoreDTO responseWrapperStoreDTO;
    private UpdateStoreDTO updateStoreDTO;

    @BeforeEach
    void setUp() {
        store = TestStore.createStore();

        storeWrapperDTO = TestStore.storeWrapperDTO();

        ResponseStoreDTO responseStoreDTO = TestStore.responseStoreDTOUsingStore(store);

        responseWrapperStoreDTO = new ResponseWrapperStoreDTO(
                List.of(responseStoreDTO)
        );

        updateStoreDTO = TestStore.updateStoreDTO();
    }

    @Test
    void shouldCreateStore() throws Exception {
        String json = objectMapper.writeValueAsString(storeWrapperDTO);

        MockHttpServletResponse response = mockMvc.perform(
                post("/v1/stores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldResponseStoreAfterCreation() throws Exception {
        given(apiStore.createStore(storeWrapperDTO)).willReturn(responseWrapperStoreDTO);

        String json = objectMapper.writeValueAsString(storeWrapperDTO);

        mockMvc.perform(
                post("/v1/stores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.stores[0].name").value(storeWrapperDTO.store().name()))
                .andExpect(jsonPath("$.data.stores[0].active").value(true));
    }

    @Test
    void shouldErrorForCreateProduct() throws Exception {
        String json = "{}";

        MockHttpServletResponse response = mockMvc.perform(
                post("/v1/stores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(400, response.getStatus());
    }

    @Test
    void shouldGetStores() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/v1/stores")
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldNotReturnStore() throws Exception {
        UUID invalidId = UUID.randomUUID();
        given(apiStore.getStoreById(invalidId)).willThrow(new EntityNotFoundException("STORE-001"));

        MockHttpServletResponse response = mockMvc.perform(
                get("/v1/stores/{storeId}", invalidId)
        ).andReturn().getResponse();

        assertEquals(404, response.getStatus());
    }

    @Test
    void shouldVerifyIntegrityOfStore() throws Exception {
        given(apiStore.getStoreById(store.getId())).willReturn(responseWrapperStoreDTO);

        mockMvc.perform(
                get("/v1/stores/{storeId}", store.getId())
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.stores[0].id").value(store.getId().toString()))
                .andExpect(jsonPath("$.data.stores[0].name").value(storeWrapperDTO.store().name()))
                .andExpect(jsonPath("$.data.stores[0].active").value(true));
    }

    @Test
    void shouldUpdateStore() throws Exception {
        String json = objectMapper.writeValueAsString(updateStoreDTO);

        MockHttpServletResponse response = mockMvc.perform(
                put("/v1/stores/{storeId}", store.getId())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldDetailStore() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                get("/v1/stores/{storeId}", store.getId())
        ).andReturn().getResponse();

        assertEquals(200, response.getStatus());
    }

    @Test
    void deleteStore() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                delete("/v1/stores/{storeId}", store.getId())
        ).andReturn().getResponse();

        assertEquals(204, response.getStatus());
    }
}