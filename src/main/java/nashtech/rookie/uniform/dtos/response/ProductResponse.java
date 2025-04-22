package nashtech.rookie.uniform.dtos.response;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.entities.enums.EProductStatus;

import java.util.UUID;

@Data
@Builder
public class ProductResponse {
    private UUID id;
    private String productCode;
    private String name;
    private String material;
    private String description;
    private String imageUrl;
    private EProductStatus status;
    private Double price;
    private float rating;
}
