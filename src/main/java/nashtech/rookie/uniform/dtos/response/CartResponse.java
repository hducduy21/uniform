package nashtech.rookie.uniform.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponse {
    private Long id;
    private ProductVariantsResponse productVariants;
    private Integer quantity;
}