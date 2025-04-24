package nashtech.rookie.uniform.inventory.internal.services;

import nashtech.rookie.uniform.inventory.internal.dtos.InventoryRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.InventoryResponse;
import nashtech.rookie.uniform.inventory.internal.dtos.InventoryUpdationRequest;

import java.util.List;

public interface InventoryService {
    InventoryResponse createInventory(InventoryRequest inventoryRequest);

    InventoryResponse updateInventory(Long id, InventoryUpdationRequest inventoryUpdationRequest);

    List<InventoryResponse> getAllInventories();

    InventoryResponse getInventoryById(Long id);
}
