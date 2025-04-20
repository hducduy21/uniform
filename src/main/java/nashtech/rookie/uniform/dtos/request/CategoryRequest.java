package nashtech.rookie.uniform.dtos.request;

import lombok.Builder;
import lombok.Data;
import nashtech.rookie.uniform.entities.enums.ECategotyStatus;

@Data
@Builder
public class CategoryRequest {
    private String name;
    private String description;
    private ECategotyStatus status;
    private Long parent;
}
