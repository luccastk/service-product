package br.com.pulsar.products.domain.services.batch;

import br.com.pulsar.products.domain.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.domain.dtos.batch.UpdateBatchDTO;
import br.com.pulsar.products.domain.models.Batch;
import br.com.pulsar.products.domain.models.Stock;

import java.util.List;
import java.util.UUID;

public interface BatchService {

    List<Batch> createBatch(Stock stock, List<CreateBatchDTO> json);

    Batch getId(UUID storeId, UUID batchId);

    Batch updateBatchAndProduct(UUID storeId, UUID batchId, UpdateBatchDTO dto);

    List<Batch> getBatchesForProduct(UUID storeId, UUID productId);
}
