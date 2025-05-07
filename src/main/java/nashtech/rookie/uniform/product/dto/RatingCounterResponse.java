package nashtech.rookie.uniform.product.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RatingCounterResponse {
    private Double averageRating;
    private Integer totalReviews;
}
