package nashtech.rookie.uniform.inventory.internal.dtos.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryRequest {
    @NotNull
    private Long productVariantsId;

    @PositiveOrZero
    @NotNull
    private Integer quantityInStock;
}
