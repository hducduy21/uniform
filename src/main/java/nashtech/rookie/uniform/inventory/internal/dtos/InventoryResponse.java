package nashtech.rookie.uniform.inventory.internal.dtos;

import nashtech.rookie.uniform.product.dto.response.ProductVariantsResponse;

public class InventoryResponse {
    private Long id;
    private ProductVariantsResponse productVariants;
    private Integer quantityInStock;
}
