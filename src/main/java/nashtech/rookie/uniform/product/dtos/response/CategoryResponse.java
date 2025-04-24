package nashtech.rookie.uniform.product.dtos.response;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.entities.Product;
import nashtech.rookie.uniform.shared.enums.ECategotyStatus;

import java.util.Set;

@Data
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private ECategotyStatus status;
    private Set<Product> products;
}
