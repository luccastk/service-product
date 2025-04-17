package br.com.pulsar.products.factory;

import br.com.pulsar.products.dtos.batch.BatchWrapperDTO;
import br.com.pulsar.products.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.dtos.http.ResponseBatchDTO;
import br.com.pulsar.products.dtos.http.ResponseWrapperBatchDTO;
import br.com.pulsar.products.models.Batch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static br.com.pulsar.products.factory.TestProduct.createProduct;
import static br.com.pulsar.products.factory.TestStock.stock;

public class TestBatch {

    private static final Integer QUANTITY_BATCH = 100;
    private static final LocalDate VALIDITY_BATCH = LocalDate.now().plusMonths(10);
    private static final LocalDateTime CREATION_TIME = LocalDateTime.now();

    public static Batch batch() {
        Batch b = new Batch();
        b.setId(UUID.randomUUID());
        b.setQuantity(QUANTITY_BATCH);
        b.setValidity(VALIDITY_BATCH);
        return b;
    }

    public static CreateBatchDTO createBatchDTO() {
        return new CreateBatchDTO(
                QUANTITY_BATCH,
                VALIDITY_BATCH
        );
    }

    public static BatchWrapperDTO batchWrapperDTO() {
        return new BatchWrapperDTO(
                List.of(createBatchDTO())
        );
    }

    public static ResponseBatchDTO responseBatchDTO() {
        return new ResponseBatchDTO(
                batch().getId(),
                createProduct().getId(),
                createProduct().getName(),
                stock().getQuantity(),
                batch().getValidity(),
                CREATION_TIME
        );
    }

    public static ResponseWrapperBatchDTO responseWrapperBatchDTO() {
        return new ResponseWrapperBatchDTO(
                List.of(responseBatchDTO())
        );
    }
}
