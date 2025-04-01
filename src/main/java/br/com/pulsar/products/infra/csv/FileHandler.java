package br.com.pulsar.products.infra.csv;

import br.com.pulsar.products.application.mappers.CSVMapper;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import br.com.pulsar.products.infra.persistence.repositories.SpringDataProductRepository;
import br.com.pulsar.products.application.batch.StockOperationsService;
import br.com.pulsar.products.application.product.ProductService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class FileHandler {

    private final SpringDataProductRepository springDataProductRepository;
    private final MessageSource m;
    private final ProductService productService;
    private final StockOperationsService batchService;
    private final CSVMapper csvMapper;

    @Transactional
    public CompletableFuture<Void> processCsv(MultipartFile file, StoreId storeId) {
        try {
            CsvToBean<ProductsCSV> csvToBean = csvConvert(file, ProductsCSV.class);
            List<ProductsCSV> csvList = csvToBean.parse();

            productService.createProduct(storeId, csvMapper.convertProductCSV(csvList));

            return CompletableFuture.completedFuture(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> CsvToBean<T> csvConvert(MultipartFile file, Class<T> type) throws IOException {
        byte[] fileBytes = file.getBytes();
        Reader reader = new InputStreamReader(new ByteArrayInputStream(fileBytes), StandardCharsets.UTF_8);

        return new CsvToBeanBuilder<T>(reader)
                .withType(type)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
    }
}
