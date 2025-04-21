package nashtech.rookie.uniform.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class WishListResponse {
    Collection<ProductGeneralResponse> products;
}
