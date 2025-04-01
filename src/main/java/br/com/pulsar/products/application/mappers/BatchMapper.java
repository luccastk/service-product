package br.com.pulsar.products.application.mappers;

import br.com.pulsar.products.application.batch.dtos.ResponseBatchDTO;
import br.com.pulsar.products.domain.entities.Batch;
import br.com.pulsar.products.domain.entities.Product;
import br.com.pulsar.products.domain.entities.Stock;
import br.com.pulsar.products.infra.persistence.entities.BatchEntity;
import br.com.pulsar.products.infra.persistence.entities.ProductEntity;

import java.util.List;

public interface BatchMapper {

    BatchEntity toEntity(Batch entityDomain, ProductEntity entity);

    List<BatchEntity> toEntity(List<Batch> entityDomain, ProductEntity entity);

    ResponseBatchDTO toDTOResponse(Batch batch);

    List<ResponseBatchDTO> toDTOResponse(List<Batch> batch);

    Batch toDomain(BatchEntity entity);

    List<Batch> toDomain(List<BatchEntity> entity);
}
