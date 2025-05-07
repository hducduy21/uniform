package nashtech.rookie.uniform.product.internal.mappers;

import nashtech.rookie.uniform.product.dto.RatingCounterResponse;
import nashtech.rookie.uniform.product.internal.entities.RatingCounter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RatingCounterMapper {
    @Mapping(target = "averageRating", expression = "java(ratingCounter.getTotalReviews() == 0 ? 0.0 : (double) ratingCounter.getTotalRating() / ratingCounter.getTotalReviews())")
    RatingCounterResponse ratingCounterToRatingCounterResponse(RatingCounter ratingCounter);
}
