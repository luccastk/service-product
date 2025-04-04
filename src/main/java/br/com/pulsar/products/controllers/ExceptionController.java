package br.com.pulsar.products.controllers;

import br.com.pulsar.products.exceptions.DuplicationException;
import br.com.pulsar.products.presenters.ErrorPresenter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    private final MessageSource m;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorPresenter> handlerEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ErrorPresenter(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<ErrorPresenter> handlerDuplicationException(DuplicationException ex) {
        return new ResponseEntity<>(new ErrorPresenter(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 400);
        response.put("message", m.getMessage("ERROR-006", null, Locale.getDefault()));

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        response.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorPresenter> handlerValidationException(ValidationException ex) {
        return new ResponseEntity<>(new ErrorPresenter(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorPresenter> handlerException(Exception ex){
        return new ResponseEntity<>(new ErrorPresenter(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorPresenter> handlerDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(new ErrorPresenter(HttpStatus.BAD_REQUEST.value(), m.getMessage("PROD-009", null, Locale.getDefault())), HttpStatus.BAD_REQUEST);
    }
}
