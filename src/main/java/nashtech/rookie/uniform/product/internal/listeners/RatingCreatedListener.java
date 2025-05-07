package nashtech.rookie.uniform.product.internal.listeners;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.product.internal.entities.Product;
import nashtech.rookie.uniform.product.internal.entities.RatingCounter;
import nashtech.rookie.uniform.product.internal.repositories.ProductRepository;
import nashtech.rookie.uniform.product.internal.repositories.RatingCounterRepository;
import nashtech.rookie.uniform.shared.dtos.RatingCreateEvent;
import org.apache.coyote.BadRequestException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingCreatedListener {
    private final RatingCounterRepository ratingCounterRepository;
    private final ProductRepository productRepository;

    @KafkaListener(id = "create-rating-group",topics = "rating-created")
    public void ratingCreated(RatingCreateEvent event) throws BadRequestException {
        Product product = productRepository.findById(event.getProductId()).orElseThrow(
                () -> new BadRequestException("Product not found")
        );

        Optional<RatingCounter> rating = ratingCounterRepository.findByProduct_Id(event.getProductId());
        if(rating.isEmpty()) {
            createRatingCounter(event, product);
        }else{
            RatingCounter ratingCounter = rating.get();
            ratingCounter.setTotalRating(ratingCounter.getTotalRating() + event.getRating().longValue());
            ratingCounter.setTotalReviews(ratingCounter.getTotalReviews() + 1);
            ratingCounterRepository.save(ratingCounter);
        }
    }

    private void createRatingCounter(RatingCreateEvent event, Product product) {
        RatingCounter ratingCounter = RatingCounter.builder()
                .totalRating(event.getRating().longValue())
                .product(product)
                .totalReviews(1)
                .build();
        ratingCounterRepository.save(ratingCounter);
    }
}
