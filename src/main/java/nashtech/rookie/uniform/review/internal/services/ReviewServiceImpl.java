package nashtech.rookie.uniform.review.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.application.utils.SecurityUtil;
import nashtech.rookie.uniform.product.api.ProductServiceProvider;
import nashtech.rookie.uniform.review.internal.dtos.ReviewRequest;
import nashtech.rookie.uniform.review.internal.entities.Rating;
import nashtech.rookie.uniform.review.internal.repositories.RatingRepository;
import nashtech.rookie.uniform.shared.dtos.RatingCreateEvent;
import nashtech.rookie.uniform.shared.dtos.RatingUpdatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final RatingRepository ratingRepository;
    private final ProductServiceProvider productServiceProvider;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public int getMyRating(UUID productId) {
        UUID userId = getCurrentUserId();
        return ratingRepository.findReviewByProductIdAndUserId(productId, userId)
                .map(Rating::getRating)
                .orElse(0);
    }

    @Transactional
    @Override
    public void productReview(ReviewRequest reviewRequest) {
        UUID userId = getCurrentUserId();

        UUID productId = reviewRequest.getProductId();
        if(!productServiceProvider.productExists(productId)) {
            throw new IllegalArgumentException("Product not found");
        }

        Optional<Rating> review = findReview(productId, userId);
        if (review.isPresent()) {
            updateReview(review.get(), reviewRequest.getRating());
        } else {
            createReview(productId, userId, reviewRequest.getRating());
        }
    }

    private void updateReview(Rating review, Integer rating) {
        int oldRating = review.getRating();
        review.setRating(rating);
        ratingRepository.save(review);

        RatingUpdatedEvent ratingUpdatedEvent = RatingUpdatedEvent.builder()
                .productId(review.getProductId())
                .ratingDelta(rating - oldRating)
                .build();
        kafkaTemplate.send("rating-updated", ratingUpdatedEvent);
    }

    private void createReview(UUID productId, UUID userId, Integer rating) {
        Rating review = Rating.builder().userId(userId).productId(productId).rating(rating).build();
        ratingRepository.save(review);

        RatingCreateEvent ratingCreateEvent = RatingCreateEvent.builder()
            .productId(productId)
            .rating(rating)
            .build();
        kafkaTemplate.send("rating-created", ratingCreateEvent);
    }

    private Optional<Rating> findReview(UUID productId, UUID userId) {
        return ratingRepository.findReviewByProductIdAndUserId(productId, userId);
    }

    private UUID getCurrentUserId() {
        return SecurityUtil.getCurrentUserId();
    }
}
