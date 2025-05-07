package nashtech.rookie.uniform.product.internal.repositories;

import nashtech.rookie.uniform.product.internal.entities.RatingCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RatingCounterRepository extends JpaRepository<RatingCounter, UUID> {
    Optional<RatingCounter> findByProduct_Id(UUID productId);
}
