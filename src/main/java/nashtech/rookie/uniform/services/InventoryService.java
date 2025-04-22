package nashtech.rookie.uniform.services;

import nashtech.rookie.uniform.dtos.request.InventoryRequest;
import nashtech.rookie.uniform.dtos.request.InventoryUpdationRequest;
import nashtech.rookie.uniform.dtos.response.InventoryResponse;

import java.util.List;

public interface InventoryService {
    InventoryResponse createInventory(InventoryRequest inventoryRequest);

    InventoryResponse updateInventory(Long id, InventoryUpdationRequest inventoryUpdationRequest);

    List<InventoryResponse> getAllInventories();

    InventoryResponse getInventoryById(Long id);
}
