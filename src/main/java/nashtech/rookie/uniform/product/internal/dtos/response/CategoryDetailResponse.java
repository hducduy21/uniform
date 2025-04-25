package nashtech.rookie.uniform.product.internal.dtos.response;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.internal.entities.enums.ECategotyStatus;
import nashtech.rookie.uniform.product.internal.entities.Product;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class CategoryDetailResponse {
    private Long id;
    private String name;
    private String description;
    private ECategotyStatus status;
    private CategoryGeneralResponse parent;
    private Set<Product> products;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}