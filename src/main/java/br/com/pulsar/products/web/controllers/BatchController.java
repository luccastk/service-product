package br.com.pulsar.products.web.controllers;

import br.com.pulsar.products.application.batch.StockOperationsService;
import br.com.pulsar.products.application.batch.dtos.CreateBatchDTO;
import br.com.pulsar.products.application.batch.dtos.ResponseBatchDTO;
import br.com.pulsar.products.application.batch.dtos.UpdateBatchDTO;
import br.com.pulsar.products.domain.valueobjects.BatchId;
import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import br.com.pulsar.products.presenters.DataPresenter;
import br.com.pulsar.products.presenters.ErrorPresenter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/product-service/v1/stores")
@Tag(name = "Batch management", description = "APIs for managing batches")
@RequiredArgsConstructor
public class BatchController {

    private final StockOperationsService batchService;

    @Operation(summary = "Create batch", description = "create a batch for a product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBatchDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @PostMapping("/{storeId}/batches/{productId}")
    public ResponseEntity<DataPresenter<ResponseBatchDTO>> createBatch(@PathVariable StoreId storeId,
                                                                             @PathVariable ProductId productId,
                                                                             @RequestBody @Valid CreateBatchDTO dto) {
        return ResponseEntity.ok(new DataPresenter<>(batchService.addBatchToStock(storeId, productId, dto)));
    }

    @Operation(summary = "Get batch", description = "Get a list of bathes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBatchDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @GetMapping("/{storeId}/batches")
    public ResponseEntity<DataPresenter<List<ResponseBatchDTO>>> getBatchMovements(
            @PathVariable StoreId storeId
    ){
       return ResponseEntity.ok(new DataPresenter<>(batchService.getBatches(storeId)));
    }

    @Operation(summary = "Get batch detail", description = "Get a list of batches with details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBatchDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @GetMapping("/{storeId}/batches/{batchId}")
    public ResponseEntity<DataPresenter<ResponseBatchDTO>> getSingleBatch(
            @PathVariable StoreId storeId,
            @PathVariable BatchId batchId
    ){
        return ResponseEntity.ok(new DataPresenter<>(batchService.detailBatch(storeId, batchId)));
    }

    @Operation(summary = "Update batch", description = "Update batch based on Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBatchDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @PutMapping("/{storeId}/batches/{batchId}")
    public ResponseEntity<DataPresenter<ResponseBatchDTO>> updateBatchAndProduct(
            @PathVariable StoreId storeId,
            @PathVariable BatchId batchId,
            @RequestBody UpdateBatchDTO dto
    ){
        return ResponseEntity.ok((new DataPresenter<>(batchService.updateBatchInStock(storeId, batchId, dto))));
    }

    @Operation(summary = "Get products expiring", description = "Get a list of products with validity expiring soon.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBatchDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @GetMapping("/{storeId}/batches/expiring")
    public ResponseEntity<DataPresenter<List<ResponseBatchDTO>>> expiringProducts(
            @PathVariable StoreId storeId,
            @PathVariable Long months
    ){
        return ResponseEntity.ok(new DataPresenter<>(batchService.verifyValidity(storeId, months)));
    }
}
