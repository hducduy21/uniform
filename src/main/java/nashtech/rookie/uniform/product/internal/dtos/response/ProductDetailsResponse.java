package nashtech.rookie.uniform.product.internal.dtos.response;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.dto.response.ProductVariantsResponse;
import nashtech.rookie.uniform.product.internal.entities.enums.EProductStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class ProductDetailsResponse {
    private UUID id;
    private String productCode;
    private String name;
    private String material;
    private String description;
    private String imageUrl;
    private EProductStatus status;
    private float rating;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String lastUpdatedBy;

    private Set<ProductVariantsResponse> productVariants;

    private Set<CategoryResponse> categories;
}
