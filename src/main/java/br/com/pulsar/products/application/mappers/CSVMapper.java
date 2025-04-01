package br.com.pulsar.products.application.mappers;

import br.com.pulsar.products.application.product.dtos.BulkCreateProductDTO;
import br.com.pulsar.products.infra.csv.ProductsCSV;

import java.util.List;

public interface CSVMapper {
    BulkCreateProductDTO convertProductCSV(List<ProductsCSV> csvList);
}
