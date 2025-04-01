package br.com.pulsar.products.domain.exceptions;

public class BusinessRuleViolation extends RuntimeException {
    public BusinessRuleViolation(String string) {
        super(string);
    }
}
