package nashtech.rookie.uniform.product.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SizeResponse {
    private Integer id;
    private String sizeTitle;
    private List<String> elements;
}
