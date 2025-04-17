package br.com.pulsar.products.domain.mappers;

import br.com.pulsar.products.domain.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.domain.dtos.csv.ProductCsvDTO;
import br.com.pulsar.products.domain.dtos.products.CreateProductDTO;
import br.com.pulsar.products.domain.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.domain.dtos.stock.CreateStockDTO;

import java.util.List;

public class CsvToDomainMapper {

    private CsvToDomainMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static CreateProductDTO map(ProductCsvDTO csv) {
        CreateStockDTO stock = new CreateStockDTO(csv.getPrice());

        CreateBatchDTO batch = new CreateBatchDTO(csv.getBatchQuantity(), csv.getBatchValidity());

        return new CreateProductDTO(csv.getName(), stock, List.of(batch));
    }

    public static ProductWrapperDTO toWrapper(List<ProductCsvDTO> csvList) {
        List<CreateProductDTO> products = csvList.stream()
                .map(CsvToDomainMapper::map)
                .toList();

        return new ProductWrapperDTO(products);
    }
}
