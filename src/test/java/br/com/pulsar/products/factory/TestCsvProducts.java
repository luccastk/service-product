package br.com.pulsar.products.factory;

import br.com.pulsar.products.domain.dtos.csv.ProductCsvDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestCsvProducts {

    private final static String NAME_PRODUCT = "product test";
    private final static BigDecimal PRICE_PRODUCT = BigDecimal.valueOf(100);
    private final static Integer QUANTITY_PRODUCT = 100;
    private static final LocalDate VALIDITY_PRODUCT = LocalDate.now().plusMonths(10);

    public static ProductCsvDTO createProductCsvDTO() {
        ProductCsvDTO productCsvDTO = new ProductCsvDTO();
        productCsvDTO.setName(NAME_PRODUCT);
        productCsvDTO.setPrice(PRICE_PRODUCT);
        productCsvDTO.setBatchQuantity(QUANTITY_PRODUCT);
        productCsvDTO.setBatchValidity(VALIDITY_PRODUCT);
        return productCsvDTO;
    }
}
