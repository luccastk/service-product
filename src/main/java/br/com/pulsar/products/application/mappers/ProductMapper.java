package br.com.pulsar.products.application.mappers;

import br.com.pulsar.products.application.product.dtos.ResponseProductDTO;
import br.com.pulsar.products.domain.entities.Product;
import br.com.pulsar.products.infra.persistence.entities.ProductEntity;

import java.util.List;

public interface ProductMapper {

    ProductEntity toEntity(Product entityDomain);

    List<ProductEntity> toEntity(List<Product> entityDomain);

    ResponseProductDTO ToDTO(Product entityDomain);

    List<ResponseProductDTO> ToDTO(List<Product> entityDomain);

    Product toDomain(ProductEntity entity);

    List<Product> toDomain(List<ProductEntity> entity);
}
