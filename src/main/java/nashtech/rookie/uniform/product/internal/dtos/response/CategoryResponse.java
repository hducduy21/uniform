package nashtech.rookie.uniform.product.internal.dtos.response;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.internal.entities.enums.ECategotyStatus;

import java.util.Set;

@Data
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private boolean isRoot;
    private String description;
    private ECategotyStatus status;
    private CategoryResponse parent;
    private Set<CategoryResponse> children;
}
