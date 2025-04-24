package nashtech.rookie.uniform.inventory.internal.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdationRequest {
    @NotNull
    private Integer quantityInStock;
}
