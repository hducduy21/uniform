package nashtech.rookie.uniform.product.internal.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductVariantsRequest {
    Map<Long, Double> productVariantCostPriceMap;
}
