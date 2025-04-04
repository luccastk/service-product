package br.com.pulsar.products.kafka;

import br.com.pulsar.products.dtos.csv.ProductCsvDTO;
import br.com.pulsar.products.dtos.kafka.FileUploadEvent;
import br.com.pulsar.products.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.exceptions.DuplicationException;
import br.com.pulsar.products.feign.FileDownload;
import br.com.pulsar.products.mappers.CsvToDomainMapper;
import br.com.pulsar.products.services.csv.CsvProcessor;
import br.com.pulsar.products.services.rest.ApiProduct;
import com.pulsar.common.common_kafka.listener.AbstractKafkaListener;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FileUploadKafkaListener extends AbstractKafkaListener<FileUploadEvent> {

    private final FileDownload fileDownload;
    private final CsvProcessor csvProcessor;
    private final ApiProduct restProduct;

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
        ResponseEntity<Resource> response = fileDownload.downloadFile(payload.request_id());

        try (InputStream is = Objects.requireNonNull(response.getBody()).getInputStream()) {
            List<ProductCsvDTO> products = csvProcessor.parse(is);
            products = products.stream().filter(product -> product.getBatchValidity() != null)
                    .toList();
            ProductWrapperDTO json = CsvToDomainMapper.toWrapper(products);
            try {
                restProduct.createProduct(payload.store_id(), json);
            } catch (DuplicationException e) {
                System.err.println("Produto já cadastrado. Ignorando duplicata para: " + json);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar arquivo CSV", e);
        }
    }
}
