package br.com.pulsar.products.domain.services.csv;

import br.com.pulsar.products.domain.dtos.kafka.FileUploadEvent;
import br.com.pulsar.products.infra.feign.FileDownload;
import br.com.pulsar.products.infra.kafka.PublishStatusProduct;
import br.com.pulsar.products.domain.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileHandler {

    private final FileDownload fileDownload;
    private final ProductService productService;
    private final PublishStatusProduct publishStatusProduct;

    public void process(FileUploadEvent event) {

        ResponseEntity<byte[]> response = fileDownload.downloadFile(event.request_id());
        InputStream is = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));

        try (is) {
            productService.convertCsvToEntity(event.storeId(), is);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar arquivo CSV", e);
        }

        publishStatusProduct.send(event.request_id());
    }
}