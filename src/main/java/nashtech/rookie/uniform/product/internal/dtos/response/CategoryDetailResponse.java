package nashtech.rookie.uniform.product.internal.dtos.response;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.internal.entities.enums.ECategotyStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class CategoryDetailResponse {
    private Long id;
    private String name;
    private String description;
    private ECategotyStatus status;
    private CategoryGeneralResponse parent;
    private boolean isRoot;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}