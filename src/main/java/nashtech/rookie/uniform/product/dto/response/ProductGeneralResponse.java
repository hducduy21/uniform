package nashtech.rookie.uniform.product.dto.response;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.internal.dtos.response.CategoryResponse;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class ProductGeneralResponse {
    private UUID id;
    private String productCode;
    private String name;
    private String imageUrl;
    private Set<CategoryResponse> categories;
}
