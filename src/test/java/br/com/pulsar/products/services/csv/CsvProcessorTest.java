package br.com.pulsar.products.services.csv;

import br.com.pulsar.products.dtos.csv.ProductCsvDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CsvProcessorTest {

    @InjectMocks
    private CsvProcessor csvProcessor;

    private ByteArrayInputStream iS;
    private ByteArrayInputStream iSInvalid;

    @BeforeEach
    void setUp() {
        String csv = """
                name,price,quantity,validity
                Product Test,100.00,10,2025-10-10
                """;

        iS = new ByteArrayInputStream(csv.getBytes(StandardCharsets.UTF_8));

        String invalidCsv = """
                name,price,quantity,validity
                Product Test,abc,a,2025-10-10
                """;

        iSInvalid = new ByteArrayInputStream(invalidCsv.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void shouldParseCSV() {
        BigDecimal expect = new BigDecimal("100.00");

        List<ProductCsvDTO> result = csvProcessor.parse(iS);

        assertNotNull(result);
        assertEquals(result.get(0).getName(), "Product Test");
        assertEquals(result.get(0).getPrice(), expect);
        assertEquals(result.get(0).getBatchQuantity(), 10);
        assertEquals(result.get(0).getBatchValidity(), LocalDate.of(2025,10,10));
    }

    @Test
    void shouldThrowException() {
        assertThrows(RuntimeException.class, () -> csvProcessor.parse(iSInvalid));
    }
}