package nashtech.rookie.uniform.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductVariantsResponse {
    private Long id;
    private String size;
    private String color;
    private String imageUrl;
    private Double costPrice;
    private Integer quantityInStock;
}
