package nashtech.rookie.uniform.product.internal.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SizeRequest {
    @NotNull
    private String name;

    @NotNull
    private Set<String> elements;
}
