package nashtech.rookie.uniform.product.internal.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductVariantsRequest {
    private Collection<ProductVariantCostPrice> productVariantCostPrice;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ProductVariantCostPrice {
        Long productVariantId;
        Double costPrice;
    }
}
