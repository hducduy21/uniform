package nashtech.rookie.uniform.inventory.api;

import java.util.Collection;
import java.util.Map;

public interface InventoryProvider {
    Integer getInventoryQuantityByProductVariantId(Long productVariantId);
    Map<Long, Integer> getInventoryQuantityByProductVariantIds(Collection<Long> productVariantIds);
}
