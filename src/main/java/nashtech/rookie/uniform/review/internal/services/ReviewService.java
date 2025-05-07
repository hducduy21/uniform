package nashtech.rookie.uniform.review.internal.services;

import nashtech.rookie.uniform.review.internal.dtos.ReviewRequest;

import java.util.UUID;

public interface ReviewService {
    int getMyRating(UUID productId);
    void productReview(ReviewRequest reviewRequest);
}
