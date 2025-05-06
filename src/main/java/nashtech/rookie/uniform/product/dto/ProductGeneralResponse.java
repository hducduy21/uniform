package nashtech.rookie.uniform.product.dto;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryResponse;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class ProductGeneralResponse {
    private UUID id;
    private String code;
    private String name;
    private Long views;
    private Set<ProductVariantsResponse> productVariants;

    private String imageUrl;
    private CategoryResponse categories;
}
