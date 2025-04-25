package nashtech.rookie.uniform.review.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.review.dtos.ReviewRequest;
import nashtech.rookie.uniform.review.entities.Review;
import nashtech.rookie.uniform.review.repositories.ReviewRepository;
import nashtech.rookie.uniform.shared.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    @Override
    public void productReview(ReviewRequest reviewRequest) {
        UUID userId = getCurrentUserId();

        UUID productId = reviewRequest.getProductId();
        // client: get product by id if the product exists

        Optional<Review> review = findReview(productId, userId);
        if (review.isPresent()) {
            updateReview(review.get(), reviewRequest.getRating());
        } else {
            createReview(productId, userId, reviewRequest.getRating());
        }
    }

    private void updateReview(Review review, Integer rating) {
        review.setRating(rating);
        saveReview(review);
    }

    private void createReview(UUID productId, UUID userId, Integer rating) {
        Review review = Review.builder().userId(userId).productId(productId).rating(rating).build();
        saveReview(review);
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
