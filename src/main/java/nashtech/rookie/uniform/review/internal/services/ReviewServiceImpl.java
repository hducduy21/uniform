package nashtech.rookie.uniform.review.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.application.utils.SecurityUtil;
import nashtech.rookie.uniform.product.api.ProductServiceProvider;
import nashtech.rookie.uniform.review.internal.dtos.ReviewRequest;
import nashtech.rookie.uniform.review.internal.entities.Review;
import nashtech.rookie.uniform.review.internal.repositories.ReviewRepository;
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
    private final ReviewRepository reviewRepository;
    private final ProductServiceProvider productServiceProvider;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    @Override
    public void productReview(ReviewRequest reviewRequest) {
        UUID userId = getCurrentUserId();

        UUID productId = reviewRequest.getProductId();
        if(!productServiceProvider.productExists(productId)) {
            throw new IllegalArgumentException("Product not found");
        }

        Optional<Review> review = findReview(productId, userId);
        if (review.isPresent()) {
            updateReview(review.get(), reviewRequest.getRating());
        } else {
            createReview(productId, userId, reviewRequest.getRating());
        }
    }

    private void updateReview(Review review, Integer rating) {
        int oldRating = review.getRating();
        review.setRating(rating);
        saveReview(review);

        RatingUpdatedEvent ratingUpdatedEvent = RatingUpdatedEvent.builder()
                .productId(review.getProductId())
                .ratingDelta(rating - oldRating)
                .build();
        kafkaTemplate.send("rating-updated", ratingUpdatedEvent);
    }

    private void createReview(UUID productId, UUID userId, Integer rating) {
        Review review = Review.builder().userId(userId).productId(productId).rating(rating).build();
//        saveReview(review);

        RatingCreateEvent ratingCreateEvent = RatingCreateEvent.builder()
            .productId(productId)
            .rating(rating)
            .build();
        kafkaTemplate.send("rating-created", ratingCreateEvent);
    }

    private Optional<Review> findReview(UUID productId, UUID userId) {
        return reviewRepository.findReviewByProductIdAndUserId(productId, userId);
    }

    private UUID getCurrentUserId() {
        return SecurityUtil.getCurrentUserId();
    }

    private void saveReview(Review review) {
        reviewRepository.save(review);
    }
}
