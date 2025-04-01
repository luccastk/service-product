package br.com.pulsar.products.presenters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DataPresenter<T> {

    private Integer statusCode;
    private LocalDateTime timestamp;
    private T data;

    public DataPresenter(T data) {
        statusCode = HttpStatus.OK.value();
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }
}
