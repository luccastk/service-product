package br.com.pulsar.products.common.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.springframework.context.MessageSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class LocalDateConverter extends AbstractBeanField<LocalDate, String> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private MessageSource m;

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        if (s == null || s.trim().isEmpty() || s.trim().equals("-")) {
            return null;
        }

        try {
            return LocalDate.parse(s, formatter);
        } catch (DateTimeParseException e) {
            throw new CsvDataTypeMismatchException(m.getMessage("ERROR-008", null, Locale.getDefault()));
        }
    }
}
