package br.com.pulsar.products.kafka;

import br.com.pulsar.products.dtos.kafka.FileUploadEvent;
import br.com.pulsar.products.exceptions.DuplicationException;
import br.com.pulsar.products.services.csv.FileHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FileUploadKafkaListener {

    private final FileHandler fileHandler;

    @RetryableTopic(
            backoff = @Backoff(delay = 3000L),
            autoCreateTopics = "true",
            include = {DuplicationException.class, EntityNotFoundException.class}
    )
    @KafkaListener(
            topics = "file.upload",
            groupId = "file-upload-consumer"
    )
    public void onMessage(FileUploadEvent event, Acknowledgment ack) {
        ack.acknowledge();
        fileHandler.process(event);
    }
}
