package br.com.pulsar.products.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocalDateConverterTest {

    @InjectMocks
    private LocalDateConverter localDateConverter;

    @Test
    void shouldReturnNullForWrongDate() {
        String validityDate = "-";

        LocalDate date = localDateConverter.convert(validityDate);

        assertNull(date);
    }

    @Test
    void shouldReturnLocalDate() {
        String validityDate = "1999-01-01";

        LocalDate date = localDateConverter.convert(validityDate);

        assertNotNull(date);
        assertInstanceOf(LocalDate.class, date);
    }
}