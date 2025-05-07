package nashtech.rookie.uniform.shared.dtos;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RatingCreateEvent {
    private UUID productId;
    private Integer rating;
}
