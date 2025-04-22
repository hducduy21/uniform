package nashtech.rookie.uniform.services;

import nashtech.rookie.uniform.dtos.request.ReviewRequest;

public interface ReviewService {
    void productReview(ReviewRequest reviewRequest);
}
