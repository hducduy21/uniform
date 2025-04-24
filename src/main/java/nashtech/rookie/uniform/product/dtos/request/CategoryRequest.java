package nashtech.rookie.uniform.product.dtos.request;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.shared.enums.ECategotyStatus;

@Data
@Builder
public class CategoryRequest {
    private String name;

    private String description;
    private ECategotyStatus status;
    private Long parent;
}
