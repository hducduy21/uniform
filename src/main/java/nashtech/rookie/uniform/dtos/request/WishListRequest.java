package nashtech.rookie.uniform.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class WishListRequest {
    @NotNull
    private UUID productId;
}
