package nashtech.rookie.uniform.review.internal.services;

import nashtech.rookie.uniform.review.internal.dtos.ReviewRequest;

public interface ReviewService {
    void productReview(ReviewRequest reviewRequest);
}
