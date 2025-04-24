package nashtech.rookie.uniform.inventory.internal.mappers;

import nashtech.rookie.uniform.inventory.internal.dtos.InventoryRequest;
import nashtech.rookie.uniform.inventory.internal.dtos.InventoryResponse;
import nashtech.rookie.uniform.inventory.internal.entities.Inventory;
import nashtech.rookie.uniform.product.repositories.ProductVariantsRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductVariantsRepository.class})
public interface InventoryMapper {
    @Mapping(target = "productVariants", ignore = true)
    Inventory inventoryRequestToInventory(InventoryRequest inventoryRequest);
    Inventory inventoryResponseToInventory(InventoryResponse inventoryResponse);

    InventoryResponse inventoryToInventoryResponse(Inventory inventory);
}
