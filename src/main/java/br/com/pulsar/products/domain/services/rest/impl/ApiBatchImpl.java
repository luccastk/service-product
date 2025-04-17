package br.com.pulsar.products.domain.services.rest.impl;

import br.com.pulsar.products.domain.dtos.batch.BatchWrapperDTO;
import br.com.pulsar.products.domain.dtos.batch.UpdateBatchDTO;
import br.com.pulsar.products.domain.dtos.http.ResponseWrapperBatchDTO;
import br.com.pulsar.products.domain.mappers.BatchMapper;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Stock;
import br.com.pulsar.products.domain.services.batch.BatchService;
import br.com.pulsar.products.domain.services.find.FindService;
import br.com.pulsar.products.domain.services.rest.ApiBatch;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ApiBatchImpl implements ApiBatch {

    private final BatchService batchService;
    private final BatchMapper batchMapper;
    private final FindService find;

    public ApiBatchImpl(BatchService batchService, BatchMapper batchMapper, FindService find) {
        this.batchService = batchService;
        this.batchMapper = batchMapper;
        this.find = find;
    }

    @Override
    public ResponseWrapperBatchDTO addBatchProduct(UUID storeId, UUID productID, BatchWrapperDTO json) {

        Product product = find.findProductByStoreAndId(storeId, productID);

        Stock stock = product.getStock();

        return new ResponseWrapperBatchDTO(
                batchMapper.toDTOResponse(
                        batchService.createBatch(stock, json.batches())
                )
        );
    }

    @Override
    public ResponseWrapperBatchDTO getBatchById(UUID storeId, UUID batchId) {
        return new ResponseWrapperBatchDTO(
                List.of(batchMapper.toDTOResponse(
                        batchService.getId(storeId, batchId)
                ))
        );
    }

    @Override
    public ResponseWrapperBatchDTO updateBatch(UUID storeId, UUID batchId, UpdateBatchDTO json) {
        return new ResponseWrapperBatchDTO(
                List.of(batchMapper.toDTOResponse(
                        batchService.updateBatchAndProduct(storeId, batchId, json)
                ))
        );
    }

    @Override
    public ResponseWrapperBatchDTO listBatchesByProductId(UUID storeId, UUID productId) {
        return new ResponseWrapperBatchDTO(
                batchMapper.toDTOResponse(
                        batchService.getBatchesForProduct(storeId, productId)
                )
        );
    }
}
