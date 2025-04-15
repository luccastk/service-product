package br.com.pulsar.products.controllers;

import br.com.pulsar.products.dtos.batch.BatchWrapperDTO;
import br.com.pulsar.products.dtos.batch.UpdateBatchDTO;
import br.com.pulsar.products.dtos.http.ResponseBatchDTO;
import br.com.pulsar.products.dtos.http.ResponseWrapperBatchDTO;
import br.com.pulsar.products.presenters.DataPresenter;
import br.com.pulsar.products.presenters.ErrorPresenter;
import br.com.pulsar.products.services.rest.ApiBatch;
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
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/v1/stores")
@Tag(name = "Batch management", description = "APIs for managing batches")
@RequiredArgsConstructor
public class BatchController {

    private final ApiBatch apiBatch;

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
    public ResponseEntity<DataPresenter<ResponseWrapperBatchDTO>> createBatch(@PathVariable UUID storeId,
                                                                                    @PathVariable UUID productId,
                                                                                    @RequestBody @Valid BatchWrapperDTO json) {
        return ResponseEntity.ok(new DataPresenter<>(apiBatch.addBatchProduct(storeId, productId, json)));
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
    @GetMapping("/{storeId}/product/{productId}")
    public ResponseEntity<DataPresenter<ResponseWrapperBatchDTO>> getBatchMovements(
            @PathVariable UUID storeId,
            @PathVariable UUID productId
    ){
       return ResponseEntity.ok(new DataPresenter<>(apiBatch.listBatchesByProductId(storeId, productId)));
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
    public ResponseEntity<DataPresenter<ResponseWrapperBatchDTO>> getSingleBatch(
            @PathVariable UUID storeId,
            @PathVariable UUID batchId
    ){
        return ResponseEntity.ok(new DataPresenter<>(apiBatch.getBatchById(storeId, batchId)));
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
    public ResponseEntity<DataPresenter<ResponseWrapperBatchDTO>> updateBatchAndProduct(
            @PathVariable UUID storeId,
            @PathVariable UUID batchId,
            @RequestBody UpdateBatchDTO dto
    ){
        return ResponseEntity.ok(new DataPresenter<>(apiBatch.updateBatch(storeId, batchId, dto)));
    }

    @Operation(summary = "Get products expiring", description = "Get a list of products with validity expiring soon.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBatchDTO.class)) }),
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
    public ResponseEntity<DataPresenter<List<ResponseWrapperBatchDTO>>> expiringProducts(
            @PathVariable UUID storeId
    ){
        return ResponseEntity.ok(new DataPresenter<>());
    }
}
