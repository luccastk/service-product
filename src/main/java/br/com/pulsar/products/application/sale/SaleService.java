package br.com.pulsar.products.application.sale;

import br.com.pulsar.products.application.sale.dtos.RequestSaleDTO;
import br.com.pulsar.products.application.sale.dtos.ResponseSaleDTO;

import java.util.UUID;

public interface SaleService {

    ResponseSaleDTO registerSale(UUID storeId, RequestSaleDTO request);
}
