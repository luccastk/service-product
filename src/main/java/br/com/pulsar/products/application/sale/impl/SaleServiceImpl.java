package br.com.pulsar.products.application.sale.impl;

import br.com.pulsar.products.application.batch.StockOperationsService;
import br.com.pulsar.products.application.product.ProductService;
import br.com.pulsar.products.application.sale.SaleService;
import br.com.pulsar.products.application.sale.dtos.RequestSaleDTO;
import br.com.pulsar.products.application.sale.dtos.ResponseSaleDTO;
import br.com.pulsar.products.application.sale.dtos.CreatePaymentDTO;
import br.com.pulsar.products.application.sale.dtos.CreateSaleDetailDTO;
import br.com.pulsar.products.application.store.StoreService;
import br.com.pulsar.products.application.mappers.PaymentMapper;
import br.com.pulsar.products.application.mappers.SaleDetailMapper;
import br.com.pulsar.products.application.mappers.SaleMapper;
import br.com.pulsar.products.infra.persistence.entities.BatchEntity;
import br.com.pulsar.products.infra.payment.Payment;
import br.com.pulsar.products.infra.sale.Sale;
import br.com.pulsar.products.infra.saledetail.SaleDetail;
import br.com.pulsar.products.infra.sale.SaleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final SaleDetailMapper saleDetailMapper;
    private final PaymentMapper paymentMapper;
    private final StoreService storeService;
    private final StockOperationsService batchService;
    private final ProductService productService;
    private final MessageSource m;

    @Transactional
    @Override
    public ResponseSaleDTO registerSale(UUID storeId, RequestSaleDTO request) {
//
//        Store store = storeService.findStore(storeId);
//        Sale sale = new Sale();
//        sale.setStore(store);
//        sale.setEmployeeId("123456789");
//
//        List<SaleDetail> saleDetails = processSaleDetails(request.salesDetail(), storeId, sale);
//        sale.setSaleDetails(saleDetails);
//
//        validateStockAvailable(saleDetails, request.salesDetail());
//
//        List<Payment> payments = processPayments(request.payments(), sale);
//        sale.setPayment(payments);
//
//        BigDecimal totalSale = calculateTotalSale(saleDetails);
//        BigDecimal totalPayments = processTotalPayment(request.payments());
//
//        comparePrices(totalSale, totalPayments);
//
//        sale.setTotal(totalSale);
//        saleRepository.save(sale);
//
//        return saleMapper.toResponseDTO(sale);
        return null;
    }

    private void validateStockAvailable(List<SaleDetail> saleDetails, List<CreateSaleDetailDTO> dtos) {
//        List<UUID> batchesIds = dtos.stream()
//                .map(CreateSaleDetailDTO::batchId)
//                .distinct()
//                .toList();
//
//        Map<UUID, BatchEntity> batchMap = batchService.findBatchesIds(batchesIds);
//
//        List<BatchEntity> batches = dtos.stream()
//                .map(mapping -> {
//                    BatchEntity batch = batchMap.get(mapping.batchId());
//                    verifyAndUpdateStockAndBatch(batch, mapping);
//                    return batch;
//                }).toList();
//
//        batchService.saveBatches(batches);
    }

    @Transactional
    private List<SaleDetail> processSaleDetails(List<CreateSaleDetailDTO> dtos, UUID storeId, Sale sale) {
//        return dtos.stream().map(dto -> {
//            ProductEntity product = productService.findByStoreAndProductId(storeId, dto.productId());
//            BatchEntity batch = getBatchFromRequest(dto);
//            return saleDetailMapper.toEntity(dto, product, batch, sale);
//        }).collect(Collectors.toList());
        return null;
    }

    private List<Payment> processPayments(List<CreatePaymentDTO> dtos, Sale sale) {
        return dtos.stream()
                .map(dto -> paymentMapper.toEntity(dto, sale))
                .collect(Collectors.toList());
    }

    private BigDecimal processTotalPayment(List<CreatePaymentDTO> dtos) {
        return dtos.stream()
                .map(CreatePaymentDTO::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalSale(List<SaleDetail> saleDetails) {
        return saleDetails.stream()
                .map(SaleDetail::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BatchEntity getBatchFromRequest(CreateSaleDetailDTO dto){
//        return batchService.findById(dto.batchId());
        return null;
    }

//    private void verifyAndUpdateStockAndBatch(BatchEntity batch, CreateSaleDetailDTO dto){
//        if (batch == null) {
//            throw new EntityNotFoundException(m.getMessage("BATCH-006", null, Locale.getDefault()));
//        }
//
//        if (batch.getQuantity() < dto.quantity()) {
//            throw new IllegalArgumentException(m.getMessage("STOCK-006", null, Locale.getDefault()));
//        }
//
//        batch.setQuantity(batch.getQuantity() - dto.quantity());
//        stockService.updateStock(batch, Math.negateExact(dto.quantity()));;
//    }
//
//    private void comparePrices(BigDecimal totalPayments, BigDecimal totalSale){
//        if (totalSale.compareTo(totalPayments) != 0) {
//            throw new IllegalArgumentException(m.getMessage("SALES-016", null, Locale.getDefault()));
//        }
//    }
}
