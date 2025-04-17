package br.com.pulsar.products.domain.services.batch.impl;

import br.com.pulsar.products.domain.dtos.batch.CreateBatchDTO;
import br.com.pulsar.products.domain.dtos.batch.UpdateBatchDTO;
import br.com.pulsar.products.domain.mappers.BatchMapper;
import br.com.pulsar.products.domain.models.Batch;
import br.com.pulsar.products.domain.models.Product;
import br.com.pulsar.products.domain.models.Stock;
import br.com.pulsar.products.domain.repositories.BatchRepository;
import br.com.pulsar.products.domain.services.batch.BatchService;
import br.com.pulsar.products.domain.services.find.FindService;
import br.com.pulsar.products.domain.services.stock.StockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {

    private final BatchMapper batchMapper;
    private final BatchRepository batchRepository;
    private final FindService find;
    private final StockService stockService;

    @Override
    public List<Batch> createBatch(Stock stock, List<CreateBatchDTO> json) {
        List<Batch> batches = batchMapper.toEntity(json);
        batches.forEach(i -> {
            i.setStock(stock);
        });

        stockService.updateStockQuantity(stock, sumQuantities(json));

        return batchRepository.saveAll(batches);
    }

    private Integer sumQuantities(List<CreateBatchDTO> json) {
        return json.stream()
                .mapToInt(CreateBatchDTO::quantity)
                .sum();
    }

    @Override
    public Batch getId(UUID storeId, UUID batchId) {
        return find.findByStoreAndBatchId(storeId, batchId);
    }

    @Transactional
    @Override
    public Batch updateBatchAndProduct(UUID storeId, UUID batchId, UpdateBatchDTO dto) {
        Batch batch = verifyBatchNull(storeId, batchId, dto);
        return batchRepository.save(batch);
    }

    @Override
    public List<Batch> getBatchesForProduct(UUID storeId, UUID productId) {
        Product product = find.findProductByStoreAndId(storeId, productId);
        return product.getStock().getBatches();
    }

    private Batch verifyBatchNull(UUID storeId, UUID batchId, UpdateBatchDTO dto) {
        Batch batch = find.findByStoreAndBatchId(storeId, batchId);

        if (dto.quantity() != null) {
            batch.setQuantity(dto.quantity());
        }

        if (dto.validity() != null) {
            batch.setValidity(dto.validity());
        }

        return batch;
    }
}