package br.com.pulsar.products.presenters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorPresenter {
    private int code;
    private String message;
}
