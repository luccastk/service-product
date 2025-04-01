package br.com.pulsar.products.application.batch.impl;

import br.com.pulsar.products.application.FindService;
import br.com.pulsar.products.application.batch.StockOperationsService;
import br.com.pulsar.products.application.batch.dtos.CreateBatchDTO;
import br.com.pulsar.products.application.batch.dtos.ResponseBatchDTO;
import br.com.pulsar.products.application.batch.dtos.UpdateBatchDTO;
import br.com.pulsar.products.application.mappers.BatchMapper;
import br.com.pulsar.products.domain.entities.Batch;
import br.com.pulsar.products.domain.entities.Product;
import br.com.pulsar.products.domain.entities.Stock;
import br.com.pulsar.products.domain.entities.Store;
import br.com.pulsar.products.domain.repositories.ProductRepository;
import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockOperationsImpl implements StockOperationsService {

    private final ProductRepository productRepository;
    private final BatchMapper batchMapper;
    private final FindService findService;

    @Override
    public List<ResponseBatchDTO> getBatches(StoreId storeId) {
        Product product = findService.findByStoreId(storeId);
        List<Batch> batches = product.getBatches();
        return batchMapper.toDTOResponse(batches);
    }

    @Override
    public ResponseBatchDTO addBatchToStock(StoreId storeId, ProductId productId, CreateBatchDTO json) {
        Product product = findService.findByProductIdAndStoreId(productId, storeId);
        product.addBatches(json.quantity(), json.validity());
        productRepository.save(product);

        Batch batch = product.getLastAddBatch().orElseThrow(() -> new EntityNotFoundException("Batch not found"));
        return batchMapper.toDTOResponse(batch);
    }

    @Override
    public ResponseBatchDTO updateBatchInStock(StoreId storeId, BatchId batchId, UpdateBatchDTO json) {
        Product product = findService.findByStoreId(storeId);
        product.updateBatchById(batchId, json.quantity(), json.validity());
        productRepository.save(product);

        Batch batch = product.findBatchById(batchId);
        return batchMapper.toDTOResponse(batch);
    }

    @Override
    public ResponseBatchDTO detailBatch(StoreId storeId, BatchId batchId) {
        Product product = findService.findByStoreId(storeId);
        Batch batch = product.findBatchById(batchId);
        return batchMapper.toDTOResponse(batch);
    }

    @Override
    public List<ResponseBatchDTO> verifyValidity(StoreId storeId, Long plusMonths) {
        Product product = findService.findByStoreId(storeId);
        List<Batch> batches = product.getValiditiesByMonths(plusMonths);
        return batchMapper.toDTOResponse(batches);
    }
}