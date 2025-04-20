package nashtech.rookie.uniform.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nashtech.rookie.uniform.entities.enums.EProductStatus;
import nashtech.rookie.uniform.validations.HexCode;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequest {
    @NotBlank
    private String productCode;

    @NotBlank
    private String name;

    private String material;
    private String description;
    private EProductStatus status;

    @NotNull
    private Integer sizeTypeId;

    @HexCode
    private Set<String> hexColors;
}
