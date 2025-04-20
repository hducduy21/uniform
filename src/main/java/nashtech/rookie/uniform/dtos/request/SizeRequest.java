package nashtech.rookie.uniform.dtos.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SizeRequest {
    private String name;
    private Set<String> elements;
}
