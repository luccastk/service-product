package br.com.pulsar.products.web.controllers;

import br.com.pulsar.products.application.product.ProductService;
import br.com.pulsar.products.application.product.dtos.BulkCreateProductDTO;
import br.com.pulsar.products.application.product.dtos.ResponseProductDTO;
import br.com.pulsar.products.application.product.dtos.ResponseProductPageDTO;
import br.com.pulsar.products.application.product.dtos.UpdateProductDTO;
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
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/product-service/v1/stores")
@Tag(name = "Product management", description = "APIs for managing products")
@RequiredArgsConstructor
public class  ProductController {

    private final ProductService productService;

    @Operation(summary = "Create product", description = "create a product with batch.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProductDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @PostMapping("{storeId}/products")
    public ResponseEntity<DataPresenter<List<ResponseProductDTO>>> createProduct(@PathVariable StoreId storeId, @RequestBody @Valid BulkCreateProductDTO dto) {
        return ResponseEntity.ok(new DataPresenter<>(productService.createProduct(storeId, dto)));
    }

    @Operation(summary = "Get product detail", description = "Get product detail from store.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProductDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @GetMapping("/{storeId}/products/{productId}")
    public ResponseEntity<DataPresenter<ResponseProductDTO>> getProduct(@PathVariable StoreId storeId,
                                                                        @PathVariable ProductId productId) {
        return ResponseEntity.ok(new DataPresenter<>(productService.detailProduct(storeId, productId)));
    }

    @Operation(summary = "Update product", description = "Update product active and name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProductDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @PutMapping("/{storeId}/products/{productId}")
    public ResponseEntity<DataPresenter<ResponseProductDTO>> updateProduct(@PathVariable StoreId storeId, @PathVariable ProductId productId, @RequestBody @Valid UpdateProductDTO dto) {
        return ResponseEntity.ok(new DataPresenter<>(productService.updateProductName(storeId, productId, dto)));
    }

    @Operation(summary = "Delete product", description = "Delete product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK"),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @DeleteMapping("/{storeId}/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable StoreId storeId, @PathVariable ProductId productId) {
        productService.deactivateProduct(storeId, productId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get products", description = "Get a list of products with cursos based in name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProductPageDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @GetMapping("/{storeId}/products")
    public ResponseEntity<DataPresenter<ResponseProductPageDTO>> productListStore(
            @PathVariable StoreId storeId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "50") int limit
    ) {
        return ResponseEntity.ok(new DataPresenter<>(productService.listProductPerStore(storeId, cursor, limit)));
    }
}
