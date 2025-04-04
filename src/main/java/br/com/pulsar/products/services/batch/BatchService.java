package br.com.pulsar.products.services.batch;

import br.com.pulsar.products.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.dtos.batch.UpdateBatchDTO;
import br.com.pulsar.products.models.Batch;
import br.com.pulsar.products.models.Stock;

import java.util.List;
import java.util.UUID;

public interface BatchService {

    List<Batch> createBatch(Stock stock, List<CreateBatchDTO> json);

    Batch getId(UUID storeId, UUID batchId);

    Batch updateBatchAndProduct(UUID storeId, UUID batchId, UpdateBatchDTO dto);

    List<Batch> getBatchesForProduct(UUID storeId, UUID productId);
}
