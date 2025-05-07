package nashtech.rookie.uniform.product.internal.dtos.request;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Data
@Builder
public class BulkProductStatusUpdateRequest {
    private String status;
    private Collection<UUID> productIds;
}
