package nashtech.rookie.uniform.product.internal.listeners;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.product.internal.entities.RatingCounter;
import nashtech.rookie.uniform.product.internal.repositories.ProductRepository;
import nashtech.rookie.uniform.product.internal.repositories.RatingCounterRepository;
import nashtech.rookie.uniform.shared.dtos.RatingUpdatedEvent;
import org.apache.coyote.BadRequestException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingUpdatedListener {
    private final RatingCounterRepository ratingCounterRepository;
    private final ProductRepository productRepository;

    @KafkaListener(id = "update-rating-group",topics = "rating-updated")
    public void ratingUpdated(RatingUpdatedEvent event) throws BadRequestException {
        if(event.getRatingDelta() > 5 || event.getRatingDelta() < -5) {
            throw new BadRequestException("Invalid rating delta");
        }

        productRepository.findById(event.getProductId()).orElseThrow(
                () -> new BadRequestException("Product not found")
        );

        RatingCounter rating = ratingCounterRepository.findByProduct_Id(event.getProductId()).orElseThrow(
                () -> new BadRequestException("Rating not found")
        );
        rating.setTotalRating(rating.getTotalRating() + event.getRatingDelta());

        ratingCounterRepository.save(rating);
    }
}
