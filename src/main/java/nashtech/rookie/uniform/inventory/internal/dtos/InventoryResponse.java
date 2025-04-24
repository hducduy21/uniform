package nashtech.rookie.uniform.inventory.internal.dtos;

import nashtech.rookie.uniform.product.dtos.response.ProductVariantsResponse;

public class InventoryResponse {
    private Long id;
    private ProductVariantsResponse productVariants;
    private Integer quantityInStock;
}
