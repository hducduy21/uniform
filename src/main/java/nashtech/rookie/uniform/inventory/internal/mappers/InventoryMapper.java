package nashtech.rookie.uniform.inventory.internal.mappers;

import nashtech.rookie.uniform.inventory.internal.dtos.InventoryRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.InventoryResponse;
import nashtech.rookie.uniform.inventory.internal.entities.Inventory;
import nashtech.rookie.uniform.product.internal.repositories.ProductVariantsRepository;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductVariantsRepository.class})
public interface InventoryMapper {
    Inventory inventoryRequestToInventory(InventoryRequest inventoryRequest);
    Inventory inventoryResponseToInventory(InventoryResponse inventoryResponse);

    InventoryResponse inventoryToInventoryResponse(Inventory inventory);
}
