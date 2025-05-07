package nashtech.rookie.uniform.review.internal.repositories;

import nashtech.rookie.uniform.review.internal.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    boolean existsByProductIdAndUserId(UUID productId, UUID userId);
    Optional<Rating> findReviewByProductIdAndUserId(UUID productId, UUID userId);
}
