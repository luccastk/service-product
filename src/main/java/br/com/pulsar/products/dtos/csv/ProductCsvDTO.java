package br.com.pulsar.products.dtos.csv;


import br.com.pulsar.products.util.LocalDateConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProductCsvDTO {
        @CsvBindByName(column = "name", required = true)
        private String name;
        @CsvBindByName(column = "price")
        private BigDecimal price;
        @CsvBindByName(column = "quantity")
        private Integer batchQuantity;
        @CsvCustomBindByName(column = "validity", converter = LocalDateConverter.class)
        private LocalDate batchValidity;
}
