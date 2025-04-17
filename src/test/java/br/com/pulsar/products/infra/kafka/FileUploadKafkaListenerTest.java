package br.com.pulsar.products.infra.kafka;

import br.com.pulsar.products.domain.dtos.kafka.FileUploadEvent;
import br.com.pulsar.products.domain.models.Store;
import br.com.pulsar.products.domain.services.csv.FileHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.Acknowledgment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FileUploadKafkaListenerTest {

    @Mock
    private FileHandler fileHandler;

    @Mock
    private Acknowledgment ack;

    @InjectMocks
    private FileUploadKafkaListener listener;

    @Mock
    private Store store;

    @Test
    void shouldProcessFileUploadEventAndAcknowledgeMessage() {
        FileUploadEvent event = new FileUploadEvent("123", store.getId());

        listener.onMessage(event, ack);

        verify(ack).acknowledge();
        verify(fileHandler).process(event);
    }
}