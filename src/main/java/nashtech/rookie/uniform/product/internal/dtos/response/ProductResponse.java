package nashtech.rookie.uniform.product.internal.dtos.response;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.dto.ProductVariantsResponse;
import nashtech.rookie.uniform.product.internal.entities.enums.EProductStatus;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class ProductResponse {
    private UUID id;
    private String code;
    private String name;
    private String material;
    private String description;
    private String imageUrl;
    private EProductStatus status;
    private Double price;
    private float rating;
    private Long views;
    private SizeResponse sizeType;
    private Set<ProductVariantsResponse> productVariants;
}
