package br.com.pulsar.products.services.rest;

import br.com.pulsar.products.dtos.batch.BatchWrapperDTO;
import br.com.pulsar.products.dtos.batch.UpdateBatchDTO;
import br.com.pulsar.products.dtos.http.ResponseBatchDTO;
import br.com.pulsar.products.dtos.http.ResponseWrapperBatchDTO;

import java.util.List;
import java.util.UUID;

public interface ApiBatch {

    ResponseWrapperBatchDTO addBatchProduct(UUID storeId, UUID productID, BatchWrapperDTO json);
    ResponseWrapperBatchDTO getBatchById(UUID storeId, UUID batchId);
    ResponseWrapperBatchDTO updateBatch(UUID storeId, UUID batchId, UpdateBatchDTO json);
    ResponseWrapperBatchDTO listBatchesByProductId(UUID storeId, UUID productId);
}
