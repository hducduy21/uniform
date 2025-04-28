package nashtech.rookie.uniform.review.internal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "ratings")
@Table(name = "ratings", schema = "review")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Integer rating;

//    private String review;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime createdAt= LocalDateTime.now();
}
