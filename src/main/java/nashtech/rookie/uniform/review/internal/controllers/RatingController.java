package nashtech.rookie.uniform.review.internal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.review.internal.dtos.ReviewRequest;
import nashtech.rookie.uniform.review.internal.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name="Rating", description = "Rating APIs")
@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final ReviewService reviewService;

    @Operation(summary = "Get my rating for a product")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public int getAllRatings(@RequestParam UUID productId) {
        return reviewService.getMyRating(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void rateProduct(@RequestBody @Valid ReviewRequest reviewRequest) {
        reviewService.productReview(reviewRequest);
    }
}
