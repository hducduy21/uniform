package nashtech.rookie.uniform.product.dto;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.internal.dtos.response.ProductResponse;
import nashtech.rookie.uniform.product.internal.entities.SizeGroup;

@Data
@Builder
public class ProductVariantsResponse {
    private Long id;
    private ProductResponse product;
    private SizeGroup sizeGroup;
    private String color;
    private String imageUrl;
    private Double costPrice;
    private Integer quantityInStock;
}
