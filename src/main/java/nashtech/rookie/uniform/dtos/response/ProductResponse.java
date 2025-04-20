package nashtech.rookie.uniform.dtos.response;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.entities.Category;
import nashtech.rookie.uniform.entities.ProductVariants;
import nashtech.rookie.uniform.entities.enums.EProductStatus;

import java.time.LocalDateTime;
import java.util.Set;
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
    private float rating;
}
