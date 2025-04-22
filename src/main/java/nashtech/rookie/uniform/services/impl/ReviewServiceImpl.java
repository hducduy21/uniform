package nashtech.rookie.uniform.services.impl;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.ReviewRequest;
import nashtech.rookie.uniform.entities.Product;
import nashtech.rookie.uniform.entities.Review;
import nashtech.rookie.uniform.entities.User;
import nashtech.rookie.uniform.exceptions.ResourceNotFoundException;
import nashtech.rookie.uniform.repositories.ProductRepository;
import nashtech.rookie.uniform.repositories.ReviewRepository;
import nashtech.rookie.uniform.services.ReviewService;
import nashtech.rookie.uniform.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public void productReview(ReviewRequest reviewRequest) {
        Product product = findProduct(reviewRequest.getProductId());
        User user = getCurrentUser();

        Optional<Review> review = findReview(product.getId(), user.getId());
        if (review.isPresent()) {
            updateReview(review.get(), reviewRequest.getRating());
        } else {
            createReview(product, user, reviewRequest.getRating());
        }
    }

    private void updateReview(Review review, Integer rating) {
        review.setRating(rating);
        reviewRepository.save(review);
    }

    private void createReview(Product product, User user, Integer rating) {
        Review review = Review.builder().user(user).product(product).rating(rating).build();
        reviewRepository.save(review);
    }

    private Optional<Review> findReview(UUID productId, UUID userId) {
        return reviewRepository.findReviewByProductIdAndUserId(productId, userId);
    }

    private Product findProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private User getCurrentUser() {
        return SecurityUtil.getCurrentUser();
    }

    private void saveReview(Review review) {
        reviewRepository.save(review);
    }
}
