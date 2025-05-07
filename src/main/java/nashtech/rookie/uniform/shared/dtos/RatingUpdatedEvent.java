package nashtech.rookie.uniform.shared.dtos;


import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RatingUpdatedEvent {
    private UUID productId;
    private Integer ratingDelta;
}
