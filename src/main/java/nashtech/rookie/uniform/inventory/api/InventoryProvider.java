package nashtech.rookie.uniform.inventory.api;

public interface InventoryProvider {
    Integer getInventoryQuantityByProductVariantId(Long productVariantId);
}
