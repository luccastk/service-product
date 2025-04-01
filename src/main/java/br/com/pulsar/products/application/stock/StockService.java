package br.com.pulsar.products.application.stock;

import br.com.pulsar.products.application.product.dtos.ResponseProductDTO;
import br.com.pulsar.products.application.stock.dtos.UpdateStockPriceDTO;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;

public interface StockService {

    ResponseProductDTO updateStockPrice(StoreId storeId, ProductId productId, UpdateStockPriceDTO json);

}
