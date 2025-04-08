package br.com.pulsar.products.kafka;

import br.com.pulsar.products.dtos.csv.ProductCsvDTO;
import br.com.pulsar.products.dtos.kafka.FileUploadEvent;
import br.com.pulsar.products.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.exceptions.DuplicationException;
import br.com.pulsar.products.feign.FileDownload;
import br.com.pulsar.products.mappers.CsvToDomainMapper;
import br.com.pulsar.products.services.csv.CsvProcessor;
import br.com.pulsar.products.services.csv.FileHandler;
import br.com.pulsar.products.services.rest.ApiProduct;
import com.pulsar.common.common_kafka.listener.AbstractKafkaListener;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class FileUploadKafkaListener extends AbstractKafkaListener<FileUploadEvent> {

    private final FileHandler fileHandler;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000),
            retryTopicSuffix = "-retry",
            dltTopicSuffix = "-dlq",
            exclude = {EntityNotFoundException.class},
            autoCreateTopics = "true"
    )
    @KafkaListener(
            topics = "file.upload",
            groupId = "file-upload-consumer",
            containerFactory = "fileUploadKafkaListenerContainerFactory"
    )
    public void onMessage(ConsumerRecord<String, FileUploadEvent> record) {

        processMessage(record.value());
    }

    @Override
    public void processMessage(FileUploadEvent payload) {
        fileHandler.process(payload);
    }
}
