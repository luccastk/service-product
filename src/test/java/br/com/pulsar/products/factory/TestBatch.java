package br.com.pulsar.products.factory;

import br.com.pulsar.products.domain.dtos.batch.BatchWrapperDTO;
import br.com.pulsar.products.domain.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.domain.dtos.batch.UpdateBatchDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseBatchDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseWrapperBatchDTO;
import br.com.pulsar.products.domain.models.Batch;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static br.com.pulsar.products.factory.TestProduct.createProduct;
import static br.com.pulsar.products.factory.TestStock.createStock;

public class TestBatch {

    private static final Integer QUANTITY_BATCH = 100;
    private static final LocalDate VALIDITY_BATCH = LocalDate.now().plusMonths(10);
    private static final Integer NEW_QUANTITY_BATCH = 200;
    private static final LocalDate NEW_VALIDITY_BATCH = LocalDate.now().plusMonths(20);
    private static final LocalDateTime CREATION_TIME = LocalDateTime.now();
    private static final Long PK_FOR_BATCH = 1L;

    public static Batch createBatch() {
        Batch b = new Batch();
        b.setPk(PK_FOR_BATCH);
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
                createBatch().getId(),
                createProduct().getId(),
                createProduct().getName(),
                createStock().getQuantity(),
                createBatch().getValidity(),
                CREATION_TIME
        );
    }

    public static ResponseWrapperBatchDTO responseWrapperBatchDTO() {
        return new ResponseWrapperBatchDTO(
                List.of(responseBatchDTO())
        );
    }

    public static UpdateBatchDTO updateBatchDTO() {
        return new UpdateBatchDTO(
                NEW_QUANTITY_BATCH,
                NEW_VALIDITY_BATCH
        );
    }

    public static ResponseBatchDTO updatedResponseBatchDTO() {
        return new ResponseBatchDTO(
                createBatch().getId(),
                createProduct().getId(),
                createProduct().getName(),
                updateBatchDTO().quantity(),
                updateBatchDTO().validity(),
                CREATION_TIME
        );
    }
}
