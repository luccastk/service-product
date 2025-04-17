package br.com.pulsar.products.infra.kafka;

import br.com.pulsar.products.domain.dtos.kafka.ProductCreateEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PublishStatusProductTest {

    @Mock
    private KafkaTemplate<String, ProductCreateEvent> kafkaTemplate;

    @Captor
    private ArgumentCaptor<ProductCreateEvent> productCreateEventCaptor;

    @InjectMocks
    private PublishStatusProduct publishStatusProduct;

    @Test
    void shouldSendKafkaMessageWithCorrectPayload() {
        String requestId = "123-abc";

        publishStatusProduct.send(requestId);

        verify(kafkaTemplate).send(eq("file.process.status"), eq(requestId), productCreateEventCaptor.capture());

        ProductCreateEvent eventSent = productCreateEventCaptor.getValue();
        assertEquals(requestId, eventSent.request_id());
        assertEquals("CREATED", eventSent.status());
    }
}