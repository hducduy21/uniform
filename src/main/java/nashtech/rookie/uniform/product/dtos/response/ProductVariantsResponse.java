package nashtech.rookie.uniform.product.dtos.response;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.entities.SizeGroup;

@Data
@Builder
public class ProductVariantsResponse {
    private Long id;
    private ProductResponse product;
    private SizeGroup sizeGroup;
    private String color;
    private String imageUrl;
    private Double costPrice;
}
