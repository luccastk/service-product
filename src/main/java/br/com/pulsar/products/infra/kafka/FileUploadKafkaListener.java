package br.com.pulsar.products.infra.kafka;

import br.com.pulsar.products.domain.dtos.kafka.FileUploadEvent;
import br.com.pulsar.products.exceptions.DuplicationException;
import br.com.pulsar.products.domain.services.csv.FileHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = false)
public class FileUploadKafkaListener {

    private final FileHandler fileHandler;

    @RetryableTopic(
            backoff = @Backoff(delay = 3000L),
            autoCreateTopics = "true",
            include = {DuplicationException.class, EntityNotFoundException.class},
            listenerContainerFactory = "fileUploadListenerFactory",
            kafkaTemplate = "fileUploadKafkaTemplate"
    )
    @KafkaListener(
            topics = "file.upload",
            groupId = "file-upload-consumer",
            containerFactory = "fileUploadListenerFactory"
    )
    public void onMessage(FileUploadEvent event, Acknowledgment ack) {
        ack.acknowledge();
        fileHandler.process(event);
    }
}
