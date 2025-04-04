package br.com.pulsar.products.mappers;

import br.com.pulsar.products.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.dtos.csv.ProductCsvDTO;
import br.com.pulsar.products.dtos.products.CreateProductDTO;
import br.com.pulsar.products.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.dtos.stock.CreateStockDTO;

import java.util.List;

public class CsvToDomainMapper {

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
