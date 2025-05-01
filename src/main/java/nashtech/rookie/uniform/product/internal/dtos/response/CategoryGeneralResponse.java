package nashtech.rookie.uniform.product.internal.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryGeneralResponse {
    private Long id;
    private String name;
}
