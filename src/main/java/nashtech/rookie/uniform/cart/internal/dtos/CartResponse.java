package nashtech.rookie.uniform.cart.internal.dtos;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.dto.response.ProductVariantsResponse;

@Data
@Builder
public class CartResponse {
    private Long id;
    private ProductVariantsResponse productVariants;
    private Integer quantity;
}