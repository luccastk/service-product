package br.com.pulsar.products.application.sale;

import br.com.pulsar.products.application.sale.dtos.RequestSaleDTO;
import br.com.pulsar.products.application.sale.dtos.ResponseSaleDTO;
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
import java.util.UUID;

@RestController
@RequestMapping("/product-service/v1/stores")
@Tag(name = "Sale management", description = "APIs for managing sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @Operation(summary = "Create sale", description = "Register a sale as employee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseSaleDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @PostMapping("/{storeId}/sale")
    public ResponseEntity<DataPresenter<ResponseSaleDTO>> createSale(@PathVariable UUID storeId, @RequestBody @Valid RequestSaleDTO dto) {
        return ResponseEntity.ok(new DataPresenter<>(saleService.registerSale(storeId, dto)));
    }

    @Operation(summary = "Get sales", description = "Gets store sales.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseSaleDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @GetMapping("/{storeId}/sale")
    public ResponseEntity<DataPresenter<List<ResponseSaleDTO>>> getSales(@PathVariable UUID storeId) {
        return ResponseEntity.ok(new DataPresenter<>());
    }
}
