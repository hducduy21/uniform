package nashtech.rookie.uniform.inventory.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.inventory.api.InventoryProvider;
import nashtech.rookie.uniform.inventory.internal.entities.Inventory;
import nashtech.rookie.uniform.inventory.internal.repositories.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryProviderImpl implements InventoryProvider {
    private final InventoryRepository inventoryRepository;

    @Override
    public Integer getInventoryQuantityByProductVariantId(Long productVariantId) {
        List<Inventory> inventories = inventoryRepository.findByProductVariantsId(productVariantId);

        if (inventories.isEmpty()) {
            return 0;
        }

        return inventories.stream().map(Inventory::getQuantityInStock).reduce(0, Integer::sum);
    }
}
