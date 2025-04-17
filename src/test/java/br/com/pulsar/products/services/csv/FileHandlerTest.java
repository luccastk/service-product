package br.com.pulsar.products.services.csv;

import br.com.pulsar.products.domain.dtos.csv.ProductCsvDTO;
import br.com.pulsar.products.domain.dtos.kafka.FileUploadEvent;
import br.com.pulsar.products.domain.services.csv.CsvProcessor;
import br.com.pulsar.products.domain.services.csv.FileHandler;
import br.com.pulsar.products.infra.feign.FileDownload;
import br.com.pulsar.products.infra.kafka.PublishStatusProduct;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.services.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class FileHandlerTest {

    @InjectMocks
    private FileHandler fileHandler;

    @Mock
    private ProductService productService;

    @Mock
    private FileDownload fileDownload;

    @Mock
    private CsvProcessor csvProcessor;

    @Mock
    private PublishStatusProduct publishStatusProduct;

    private String csvContent;
    private String requestId;
    private UUID storeId;

    private FileUploadEvent event;
    private ProductCsvDTO productCsvDTO;

    private Product product;

    private byte[] fileBytes;
    private InputStream iS;

    @BeforeEach
    void setUp() {
        csvContent = """
                name,price,quantity,validity
                Product Test,100.00,10,2025-10-10
                """;

        requestId = UUID.randomUUID().toString();
        storeId = UUID.randomUUID();

        event = new FileUploadEvent(
                requestId,
                storeId
        );

        productCsvDTO = new ProductCsvDTO();
        productCsvDTO.setName("Product Test");
        productCsvDTO.setPrice(BigDecimal.valueOf(100.00));
        productCsvDTO.setBatchQuantity(10);
        productCsvDTO.setBatchValidity(LocalDate.of(2025,10,10));

        fileBytes = csvContent.getBytes(StandardCharsets.UTF_8);

        product = new Product();
        product.setName("Product Test");

        iS = new ByteArrayInputStream(fileBytes);
    }

    @Test
    void shouldProcessFileAndSave() {
        given(fileDownload.downloadFile(requestId)).willReturn(ResponseEntity.ok(fileBytes));

        doNothing().when(productService).convertCsvToEntity(eq(storeId), any(InputStream.class));
        doNothing().when(publishStatusProduct).send(event.request_id());

        fileHandler.process(event);

        verify(fileDownload).downloadFile(requestId);
        verify(productService).convertCsvToEntity(eq(storeId), any(InputStream.class));
        verify(publishStatusProduct).send(requestId);
    }
}