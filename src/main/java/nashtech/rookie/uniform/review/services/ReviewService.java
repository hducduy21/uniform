package nashtech.rookie.uniform.review.services;

import nashtech.rookie.uniform.review.dtos.ReviewRequest;

public interface ReviewService {
    void productReview(ReviewRequest reviewRequest);
}
