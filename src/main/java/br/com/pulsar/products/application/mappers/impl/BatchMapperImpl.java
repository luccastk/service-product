package br.com.pulsar.products.application.mappers.impl;

import br.com.pulsar.products.application.batch.dtos.ResponseBatchDTO;
import br.com.pulsar.products.application.mappers.BatchMapper;
import br.com.pulsar.products.application.mappers.ProductMapper;
import br.com.pulsar.products.domain.entities.Batch;
import br.com.pulsar.products.domain.entities.Product;
import br.com.pulsar.products.domain.entities.Stock;
import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import br.com.pulsar.products.infra.persistence.entities.BatchEntity;
import br.com.pulsar.products.infra.persistence.entities.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BatchMapperImpl implements BatchMapper {

    @Override
    public BatchEntity toEntity(Batch batch, ProductEntity product) {
        if (batch == null) {
            return null;
        }

        BatchEntity batchEntity = new BatchEntity();

        batchEntity.setId(batch.getId());
        batchEntity.setQuantity(batch.getQuantity());
        batchEntity.setValidity(batch.getValidity());
        batchEntity.setCreateTime(batch.getCreateTime());
        batchEntity.setProduct(product);

        return batchEntity;
    }

    @Override
    public List<BatchEntity> toEntity(List<Batch> batches, ProductEntity entity) {
        return batches == null ? List.of() : batches.stream().map(i -> toEntity(i, entity)).collect(Collectors.toList());
    }

    @Override
    public ResponseBatchDTO toDTOResponse(Batch batch) {
        if (batch == null) {
            return null;
        }

        return new ResponseBatchDTO(
                new BatchId(batch.getId().id()),
                batch.getQuantity(),
                batch.getValidity(),
                batch.getCreateTime()
        );
    }

    @Override
    public List<ResponseBatchDTO> toDTOResponse(List<Batch> batch) {
        return batch == null ? List.of() : batch.stream().map(this::toDTOResponse).collect(Collectors.toList());
    }

    @Override
    public Batch toDomain(BatchEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Batch(
                entity.getId(),
                entity.getQuantity(),
                entity.getValidity(),
                entity.getCreateTime()
        );
    }

    @Override
    public List<Batch> toDomain(List<BatchEntity> entity) {
        return entity == null ? List.of() : entity.stream().map(this::toDomain).collect(Collectors.toList());
    }
}
