package nashtech.rookie.uniform.inventory.internal.mappers;

import nashtech.rookie.uniform.inventory.internal.dtos.requests.InventoryRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.response.InventoryResponse;
import nashtech.rookie.uniform.inventory.internal.entities.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory inventoryRequestToInventory(InventoryRequest inventoryRequest);
    Inventory inventoryResponseToInventory(InventoryResponse inventoryResponse);

    InventoryResponse inventoryToInventoryResponse(Inventory inventory);
}
