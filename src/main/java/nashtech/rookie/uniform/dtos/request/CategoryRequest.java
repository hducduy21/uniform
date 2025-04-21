package nashtech.rookie.uniform.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.entities.enums.ECategotyStatus;

@Data
@Builder
public class CategoryRequest {
    @NotBlank
    private String name;

    private String description;
    private ECategotyStatus status;
    private Long parent;
}
