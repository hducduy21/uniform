package nashtech.rookie.uniform.review.repositories;

import nashtech.rookie.uniform.review.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByProductIdAndUserId(UUID productId, UUID userId);
    Optional<Review> findReviewByProductIdAndUserId(UUID productId, UUID userId);
}
