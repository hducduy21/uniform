package nashtech.rookie.uniform.inventory.internal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nashtech.rookie.uniform.inventory.internal.dtos.requests.InventoryRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.requests.InventoryUpdateRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.response.InventoryResponse;
import nashtech.rookie.uniform.inventory.internal.services.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Tag(name="Inventory", description = "Inventory APIs")
@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<InventoryResponse> getAllInventories() {
        return inventoryService.getAllInventories();
    }

    @GetMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse getInventoryById(@PathVariable Long inventoryId) {
        return inventoryService.getInventoryById(inventoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createInventory(@RequestBody @Valid InventoryRequest inventoryRequest) {
        log.info("Create inventory: {}", inventoryRequest);
        return inventoryService.createInventory(inventoryRequest);
    }

    @Operation(summary = "Update quantity in stock of inventory")
    @PutMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse updateInventory(@PathVariable Long inventoryId, @RequestBody @Valid InventoryUpdateRequest inventoryRequest) {
        log.info("Update inventory with id: {} with request: {}", inventoryId, inventoryRequest);
        return inventoryService.updateInventory(inventoryId, inventoryRequest);
    }
}
