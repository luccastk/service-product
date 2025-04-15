package br.com.pulsar.products.exceptions;

import br.com.pulsar.products.dtos.products.CreateProductDTO;
import br.com.pulsar.products.mappers.ProductMapper;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Store;
import br.com.pulsar.products.repositories.ProductRepository;
import br.com.pulsar.products.services.product.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DuplicationExceptionTest {

    private CreateProductDTO createProductDTO;
    private Store store;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;


    @BeforeEach
    void setUp() {
        store = new Store();
        store.setPk(1L);
        store.setId(UUID.randomUUID());
        store.setName("Store test");
        store.setActive(true);
        store.setProducts(null);

        createProductDTO = new CreateProductDTO(
                 "Product test",
                null,
                null
        );
    }

    @Test
    void shouldNotThrowDuplicateException() {
        Product product = new Product();

        BDDMockito.given(productRepository.existsByNameIgnoringCase(createProductDTO.name())).willReturn(false);
        BDDMockito.given(productMapper.ToEntity(createProductDTO)).willReturn(product);

        assertDoesNotThrow(() -> productService.saveProducts(store, createProductDTO));
    }

    @Test
    void shouldThrowDuplicateException() {

        BDDMockito.given(productRepository.existsByNameIgnoringCase(createProductDTO.name())).willReturn(true);

        assertThrows(DuplicationException.class, () -> productService.saveProducts(store, createProductDTO));
    }
}