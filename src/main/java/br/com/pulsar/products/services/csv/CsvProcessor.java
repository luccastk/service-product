package br.com.pulsar.products.services.csv;

import br.com.pulsar.products.dtos.csv.ProductCsvDTO;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CsvProcessor {
    public List<ProductCsvDTO> parse(InputStream inputStream){
        try (BOMInputStream bomInputStream = new BOMInputStream(inputStream);
             Reader reader = new InputStreamReader(bomInputStream, StandardCharsets.UTF_8)) {

            CsvToBean<ProductCsvDTO> csvToBean = new CsvToBeanBuilder<ProductCsvDTO>(reader)
                    .withType(ProductCsvDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar CSV", e);
        }
    }
}
