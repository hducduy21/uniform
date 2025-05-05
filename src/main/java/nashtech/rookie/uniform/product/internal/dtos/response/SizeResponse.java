package nashtech.rookie.uniform.product.internal.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SizeResponse {
    private Integer id;
    private String name;
    private List<String> elements;
}
