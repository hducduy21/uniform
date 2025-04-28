package nashtech.rookie.uniform.inventory.internal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.inventory.internal.dtos.requests.InventoryRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.requests.InventoryUpdationRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.response.InventoryResponse;
import nashtech.rookie.uniform.inventory.internal.services.InventoryService;
import nashtech.rookie.uniform.shared.dtos.ApiResponse;
import nashtech.rookie.uniform.shared.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Collection<InventoryResponse>> getAllInventory() {
        Collection<InventoryResponse> inventories = inventoryService.getAllInventories();
        return ResponseUtil.successResponse(inventories);
    }

    @GetMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<InventoryResponse> getInventoryById(@PathVariable Long inventoryId) {
        InventoryResponse inventory = inventoryService.getInventoryById(inventoryId);
        return ResponseUtil.successResponse(inventory);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<InventoryResponse> createInventory(@RequestBody @Valid InventoryRequest inventoryRequest) {
        InventoryResponse inventory = inventoryService.createInventory(inventoryRequest);
        return ResponseUtil.successResponse(inventory);
    }

    @PutMapping("/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<InventoryResponse> updateInventory(@PathVariable Long inventoryId, @RequestBody @Valid InventoryUpdationRequest inventoryRequest) {
        InventoryResponse inventory = inventoryService.updateInventory(inventoryId, inventoryRequest);
        return ResponseUtil.successResponse(inventory);
    }
}
