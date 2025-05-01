package nashtech.rookie.uniform.inventory.internal.services;

import nashtech.rookie.uniform.inventory.internal.dtos.requests.InventoryRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.requests.InventoryUpdationRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.response.InventoryResponse;

import java.util.List;

public interface InventoryService {
    InventoryResponse createInventory(InventoryRequest inventoryRequest);

    InventoryResponse updateInventory(Long id, InventoryUpdationRequest inventoryUpdationRequest);

    List<InventoryResponse> getAllInventories();

    InventoryResponse getInventoryById(Long id);
}
