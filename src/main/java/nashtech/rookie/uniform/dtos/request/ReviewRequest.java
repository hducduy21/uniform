package nashtech.rookie.uniform.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    @NotNull
    private UUID productId;

    @NotNull
    @Range(min = 1, max = 5, message = "Rating must be between 1 and 5")
    private int rating;
}
