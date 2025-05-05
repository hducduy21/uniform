package nashtech.rookie.uniform.product.internal.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.internal.entities.enums.ECategotyStatus;

@Data
@Builder
public class CategoryRequest {
    @NotBlank
    private String name;
    private String description;
    private ECategotyStatus status;
    private Long parent;
}
