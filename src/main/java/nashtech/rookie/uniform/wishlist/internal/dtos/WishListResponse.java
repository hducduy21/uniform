package nashtech.rookie.uniform.wishlist.internal.dtos;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.product.dtos.response.ProductGeneralResponse;

@Data
@Builder
public class WishListResponse {
    ProductGeneralResponse product;
}
