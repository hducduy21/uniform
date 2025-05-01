package nashtech.rookie.uniform.cart.internal.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartRequest {
    @NotNull(message = "Product variant ID cannot be null")
    private Long productVariantsId;

    @Positive(message = "Quantity must be a positive number")
    private Integer quantity ;
}
