package br.com.pulsar.products.application.batch;

import br.com.pulsar.products.application.batch.dtos.CreateBatchDTO;
import br.com.pulsar.products.application.batch.dtos.ResponseBatchDTO;
import br.com.pulsar.products.application.batch.dtos.UpdateBatchDTO;
import br.com.pulsar.products.domain.entities.Stock;
import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;

import java.math.BigDecimal;
import java.util.List;

public interface StockOperationsService {

    ResponseBatchDTO addBatchToStock(StoreId storeId, ProductId productId, CreateBatchDTO json);
    ResponseBatchDTO updateBatchInStock(StoreId storeId, BatchId batchId, UpdateBatchDTO json);
    ResponseBatchDTO detailBatch(StoreId storeId, BatchId batchId);
    List<ResponseBatchDTO> getBatches(StoreId storeId);
    List<ResponseBatchDTO> verifyValidity(StoreId storeId, Long plusMonths);
}
