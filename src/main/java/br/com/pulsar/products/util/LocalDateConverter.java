package br.com.pulsar.products.util;

import com.opencsv.bean.AbstractBeanField;
import java.time.LocalDate;

public class LocalDateConverter extends AbstractBeanField<LocalDate, String> {
    @Override
    protected LocalDate convert(String value) {
        if (value == null || value.trim().equals("-")) {
            return null;
        }
        return LocalDate.parse(value);
    }
}
