package nashtech.rookie.uniform.inventory.internal.services;

import nashtech.rookie.uniform.inventory.internal.dtos.requests.InventoryRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.requests.InventoryUpdateRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.response.InventoryResponse;

import java.util.List;

public interface InventoryService {
    InventoryResponse createInventory(InventoryRequest inventoryRequest);

    InventoryResponse updateInventory(Long id, InventoryUpdateRequest inventoryUpdateRequest);

    List<InventoryResponse> getAllInventories();

    InventoryResponse getInventoryById(Long id);
}
