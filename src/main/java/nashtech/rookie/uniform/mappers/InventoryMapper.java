package nashtech.rookie.uniform.mappers;

import nashtech.rookie.uniform.dtos.request.InventoryRequest;
import nashtech.rookie.uniform.dtos.response.InventoryResponse;
import nashtech.rookie.uniform.entities.Inventory;
import nashtech.rookie.uniform.repositories.ProductVariantsRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductVariantsRepository.class})
public interface InventoryMapper {
    @Mapping(target = "productVariants", ignore = true)
    Inventory inventoryRequestToInventory(InventoryRequest inventoryRequest);
    Inventory inventoryResponseToInventory(InventoryResponse inventoryResponse);

    InventoryResponse inventoryToInventoryResponse(Inventory inventory);
}
