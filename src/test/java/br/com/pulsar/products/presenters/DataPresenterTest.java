package br.com.pulsar.products.presenters;

import br.com.pulsar.products.domain.presenters.DataPresenter;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class DataPresenterTest {

    @Test
    void shouldCreateDataPresenter() {
        String mockData = "Hello";

        DataPresenter<String> presenter = new DataPresenter<>(mockData);

        assertEquals(HttpStatus.OK.value(), presenter.getStatusCode());
        assertNotNull(presenter.getTimestamp());
        assertEquals(mockData, presenter.getData());
    }
}