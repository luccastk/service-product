package br.com.pulsar.products.application.mappers.impl;

import br.com.pulsar.products.application.product.dtos.BulkCreateProductDTO;
import br.com.pulsar.products.application.product.dtos.CreateProductListDTO;
import br.com.pulsar.products.application.mappers.CSVMapper;
import br.com.pulsar.products.infra.csv.ProductsCSV;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CSVMapperImpl implements CSVMapper {

    @Override
    public BulkCreateProductDTO convertProductCSV(List<ProductsCSV> csvList) {
//        List<CreateProductListDTO> listProducts = csvList.stream().map(csv -> {
//            CreateProductDTO product = new CreateProductDTO(csv.getName());
//            CreateStockDTO batch = new CreateStockDTO(csv.getBatchQuantity(), csv.getBatchValidity(), csv.getBatchPrice());
//            return new CreateProductListDTO(product, List.of(batch));
//        }).toList();
        return null;
    }
}
