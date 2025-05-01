package nashtech.rookie.uniform.review.internal.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.review.internal.dtos.ReviewRequest;
import nashtech.rookie.uniform.review.internal.services.ReviewService;
import nashtech.rookie.uniform.shared.dtos.ApiResponse;
import nashtech.rookie.uniform.shared.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {
    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> rateProduct(@RequestBody @Valid ReviewRequest reviewRequest) {
        reviewService.productReview(reviewRequest);
        return ResponseUtil.successResponse("Rating submitted successfully");
    }
}
