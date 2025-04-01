package br.com.pulsar.products.web.controllers;

import br.com.pulsar.products.application.store.StoreService;
import br.com.pulsar.products.application.store.dtos.CreateStoreDTO;
import br.com.pulsar.products.application.store.dtos.ResponseStoreDTO;
import br.com.pulsar.products.application.store.dtos.UpdateStoreDTO;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import br.com.pulsar.products.presenters.DataPresenter;
import br.com.pulsar.products.presenters.ErrorPresenter;
import br.com.pulsar.products.infra.csv.FileHandler;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product-service/v1/stores")
@RequiredArgsConstructor
@Tag(name = "Store management", description = "APIs for managing stores")
public class StoreController {

    private final StoreService storeService;
    private final FileHandler fileService;

    @Operation(summary = "Create a new store", description = "Creates a new store record with the provided store data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseStoreDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @PostMapping
    public ResponseEntity<DataPresenter<ResponseStoreDTO>> createStore(@RequestBody @Valid CreateStoreDTO dto) {
        return ResponseEntity.ok(new DataPresenter<>(storeService.createStore(dto)));
    }

    @Operation(summary = "Get stores", description = "Get stores from application.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseStoreDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @GetMapping
    public ResponseEntity<DataPresenter<List<ResponseStoreDTO>>> getStores() {
        return ResponseEntity.ok(new DataPresenter<>(storeService.findAllByActiveTrue()));
    }

    @Operation(summary = "Update store", description = "Update store.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseStoreDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @PutMapping("/{storeId}")
    public ResponseEntity<DataPresenter<ResponseStoreDTO>> updateStore(@PathVariable StoreId storeId, @RequestBody @Valid UpdateStoreDTO dto) {
        return ResponseEntity.ok(new DataPresenter<>(storeService.updateStoreName(storeId, dto)));
    }

    @Operation(summary = "Get store details", description = "Get store details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseStoreDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @GetMapping("/{storeId}")
    public ResponseEntity<DataPresenter<ResponseStoreDTO>> storeDetail(@PathVariable StoreId storeId) {
        return ResponseEntity.ok(new DataPresenter<>(storeService.detailStore(storeId)));
    }

    @Operation(summary = "Delete a store", description = "Marks a store as deactivated or deletes it by UUID.")
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
    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStore(@PathVariable StoreId storeId) {
        storeService.deactivateStore(storeId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Upload file", description = "Upload a csv file with products to registry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = "OK",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseStoreDTO.class)) } ),
            @ApiResponse(responseCode  = "400", description = "The request find a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "403", description = "You don't have permission to access",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "404", description = "Content Not Found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) }),
            @ApiResponse(responseCode  = "500", description = "The server have a error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorPresenter.class)) })
    })
    @PostMapping("/{storeId}/upload")
    public ResponseEntity<?> uploadCsv(@PathVariable StoreId storeId, @RequestParam("file") MultipartFile file) {
        fileService.processCsv(file, storeId);
        return ResponseEntity.ok().build();
    }
}
