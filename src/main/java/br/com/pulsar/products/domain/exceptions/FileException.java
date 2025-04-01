package br.com.pulsar.products.domain.exceptions;

public class FileException extends RuntimeException {
    public FileException(String message) {
        super(message);
    }
}
