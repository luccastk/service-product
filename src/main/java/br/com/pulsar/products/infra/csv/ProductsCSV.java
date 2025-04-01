package br.com.pulsar.products.infra.csv;

import br.com.pulsar.products.common.utils.LocalDateConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsCSV {
    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "quantity")
    private Integer batchQuantity;

    @CsvCustomBindByName(column = "validity", converter = LocalDateConverter.class)
    private LocalDate batchValidity;

    @CsvBindByName(column = "price")
    private BigDecimal batchPrice;
}
