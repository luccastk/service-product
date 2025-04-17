package br.com.pulsar.products.domain.services.rest;

import br.com.pulsar.products.domain.dtos.batch.BatchWrapperDTO;
import br.com.pulsar.products.domain.dtos.batch.UpdateBatchDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseWrapperBatchDTO;

import java.util.UUID;

public interface ApiBatch {

    ResponseWrapperBatchDTO addBatchProduct(UUID storeId, UUID productID, BatchWrapperDTO json);
    ResponseWrapperBatchDTO getBatchById(UUID storeId, UUID batchId);
    ResponseWrapperBatchDTO updateBatch(UUID storeId, UUID batchId, UpdateBatchDTO json);
    ResponseWrapperBatchDTO listBatchesByProductId(UUID storeId, UUID productId);
}
