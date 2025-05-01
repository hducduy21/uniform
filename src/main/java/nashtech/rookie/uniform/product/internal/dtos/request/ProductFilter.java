package nashtech.rookie.uniform.product.internal.dtos.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductFilter {
    private String search;
    private List<String> categories;
    private Double minPrice;
    private Double maxPrice;
    private List<String> status;
}
