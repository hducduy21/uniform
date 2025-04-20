package nashtech.rookie.uniform.dtos.response;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.entities.Product;
import nashtech.rookie.uniform.entities.SizeGroup;

@Data
@Builder
public class ProductVariantsResponse {
    private Long id;
    private Product product;
    private SizeGroup sizeGroup;
    private String color;
    private String imageUrl;
}
