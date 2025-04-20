package nashtech.rookie.uniform.dtos.response;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.entities.Product;
import nashtech.rookie.uniform.entities.enums.ECategotyStatus;

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
