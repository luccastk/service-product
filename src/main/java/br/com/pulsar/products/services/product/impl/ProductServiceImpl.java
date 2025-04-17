package br.com.pulsar.products.services.product.impl;

import br.com.pulsar.products.dtos.csv.ProductCsvDTO;
import br.com.pulsar.products.dtos.http.ResponseWrapperProductDTO;
import br.com.pulsar.products.dtos.products.CreateProductDTO;
import br.com.pulsar.products.dtos.products.ProductWrapperDTO;
import br.com.pulsar.products.dtos.products.UpdateProductDTO;
import br.com.pulsar.products.exceptions.DuplicationException;
import br.com.pulsar.products.mappers.CsvToDomainMapper;
import br.com.pulsar.products.mappers.ProductMapper;
import br.com.pulsar.products.models.Batch;
import br.com.pulsar.products.models.Product;
import br.com.pulsar.products.models.Stock;
import br.com.pulsar.products.models.Store;
import br.com.pulsar.products.repositories.ProductRepository;
import br.com.pulsar.products.services.batch.BatchService;
import br.com.pulsar.products.services.batch.impl.BatchServiceImpl;
import br.com.pulsar.products.services.csv.CsvProcessor;
import br.com.pulsar.products.services.find.FindService;
import br.com.pulsar.products.services.product.ProductService;
import br.com.pulsar.products.services.stock.StockService;
import br.com.pulsar.products.services.stock.impl.StockServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final StockService stockService;
    private final BatchService batchService;
    private final FindService find;
    private final CsvProcessor csvProcessor;

    @Transactional
    @Override
    public Product saveProducts(Store store, CreateProductDTO json) {
        verifyByName(json.name());

        Product product = productMapper.ToEntity(json);
        product.setStore(store);

        Stock stock = stockService.createStockForProduct(product, json.stock());
        product.setStock(stock);
        productRepository.save(product);

        List<Batch> batches = batchService.createBatch(stock, json.batch());
        stock.setBatches(batches);

        return product;
    }

    private void verifyByName(String name){
        if (productRepository.existsByNameIgnoringCase(name)) {
            throw new DuplicationException("Produto ja cadastrado!");
        }
    }

    @Transactional
    @Override
    public Product updateProduct(UUID storeId, UUID productId, UpdateProductDTO json) {
        Product product = verifyProductNull(storeId, productId, json);
        return productRepository.save(product);
    }

    private Product verifyProductNull(UUID storeId, UUID productId, UpdateProductDTO dto){
        Product product = find.findProductByStoreAndId(storeId, productId);

        if (dto.name() != null) {
            product.setName(dto.name());
        }

        if (dto.active() != null) {
            product.setActive(dto.active());
        }

        return product;
    }

    @Transactional
    @Override
    public Product productDetail(UUID storeId, UUID productId) {
        return find.findProductByStoreAndId(storeId, productId);
    }

    @Transactional
    @Override
    public void deActiveProduct(UUID storeId, UUID productId) {
        Product product = find.findProductByStoreAndId(storeId, productId);
        product.setActive(false);
        productRepository.save(product);
    }

    @Transactional
    @Override
    public ResponseWrapperProductDTO listProductPerStore(UUID storeId, String cursor, int limit) {
        List<Product> productList;

        if (cursor == null) {
            productList = productRepository.findProductPerStore(storeId, limit);
        } else {
            productList = productRepository.findProductPerStoreAfterCursor(storeId, cursor, limit);
        }

        String nextCursor = productList.size() == limit ? productList.get(productList.size() - 1).getName() : null;

        return new ResponseWrapperProductDTO(
                productMapper.ToDTO(productList),
                nextCursor
        );
    }

    @Override
    public void convertCsvToEntity(UUID storeId, InputStream is) {
        Store store = find.findStoreId(storeId);

        List<ProductCsvDTO> products = csvProcessor.parse(is);

        products = products.stream().filter(product -> product.getBatchValidity() != null)
                .toList();

        ProductWrapperDTO productWrapperDTO = CsvToDomainMapper.toWrapper(products);

        productWrapperDTO.products().forEach(product -> {
            saveProducts(store, product);
        });
    }
}
