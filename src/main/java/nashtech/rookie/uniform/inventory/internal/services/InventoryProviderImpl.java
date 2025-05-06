package nashtech.rookie.uniform.inventory.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.inventory.api.InventoryProvider;
import nashtech.rookie.uniform.inventory.internal.entities.Inventory;
import nashtech.rookie.uniform.inventory.internal.repositories.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryProviderImpl implements InventoryProvider {
    private final InventoryRepository inventoryRepository;

    @Override
    public Integer getInventoryQuantityByProductVariantId(Long productVariantId) {
        Collection<Inventory> inventories = inventoryRepository.findByProductVariantsId(productVariantId);

        if (inventories.isEmpty()) {
            return 0;
        }

        return inventories.stream().map(Inventory::getQuantityInStock).reduce(0, Integer::sum);
    }

    @Override
    public Map<Long, Integer> getInventoryQuantityByProductVariantIds(Collection<Long> productVariantIds) {
        Collection<Inventory> inventories = inventoryRepository.findAllByProductVariantsIdIn(productVariantIds);

        return inventories.stream()
                .collect(Collectors.groupingBy(
                        Inventory::getProductVariantsId,
                        Collectors.summingInt(Inventory::getQuantityInStock)
                ));
    }
}
