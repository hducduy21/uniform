package nashtech.rookie.uniform.product.internal.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "ratingCounters")
@Table(name = "ratingCounters", schema = "product")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RatingCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    @Column(nullable = false)
    private Long totalRating;

    @Column(nullable = false)
    private Integer totalReviews;
}
