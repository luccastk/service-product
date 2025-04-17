package br.com.pulsar.products.kafka;

import br.com.pulsar.products.dtos.kafka.ProductCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublishStatusProduct {

    private final KafkaTemplate<String, ProductCreateEvent> productCreateEventKafkaTemplate;

    public void send(String requestId) {
        ProductCreateEvent event = new ProductCreateEvent(
                requestId,
                "CREATED"
        );

        productCreateEventKafkaTemplate.send("file.process.status", requestId, event);
    }
}
