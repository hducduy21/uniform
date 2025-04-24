package nashtech.rookie.uniform.product.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nashtech.rookie.uniform.shared.validations.HexCode;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductVariantsRequest {
    List<ProductVariant> productVariants;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ProductVariant {
        @NotNull
        private Integer sizeId;

        @NotBlank
        @HexCode
        private String hexCode;
        
        @PositiveOrZero
        @NotNull
        private Double costPrice;
    }
}
