package br.com.pulsar.products.services.csv;

import br.com.pulsar.products.dtos.csv.ProductCsvDTO;
import br.com.pulsar.products.dtos.kafka.FileUploadEvent;
import br.com.pulsar.products.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.exceptions.DuplicationException;
import br.com.pulsar.products.feign.FileDownload;
import br.com.pulsar.products.mappers.CsvToDomainMapper;
import br.com.pulsar.products.services.rest.ApiProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileHandler {

    private final FileDownload fileDownload;
    private final CsvProcessor csvProcessor;
    private final ApiProduct restProduct;

    public void process(FileUploadEvent event) {
        ResponseEntity<byte[]> response = fileDownload.downloadFile(event.request_id());

        try (InputStream is = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()))) {
            List<ProductCsvDTO> products = csvProcessor.parse(is);
            products = products.stream().filter(product -> product.getBatchValidity() != null)
                    .toList();
            ProductWrapperDTO json = CsvToDomainMapper.toWrapper(products);
            try {
                restProduct.createProduct(event.storeId(), json);
            } catch (DuplicationException e) {
                System.err.println("Produto já cadastrado. Ignorando duplicata para: " + json);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar arquivo CSV", e);
        }
    }
}
