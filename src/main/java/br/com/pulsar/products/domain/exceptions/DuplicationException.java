package br.com.pulsar.products.domain.exceptions;

public class DuplicationException extends RuntimeException {
    public DuplicationException(String message) { super(message);
    }
}
