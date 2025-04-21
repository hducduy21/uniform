package nashtech.rookie.uniform.repositories;

import nashtech.rookie.uniform.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    boolean existsByUserIdAndProductVariantsId(UUID userId, Long productVariantsId);
    Optional<Cart> findByUserIdAndProductVariantsId(UUID userId, Long productVariantsId);
    Optional<Cart> findByUserId(UUID userId);
}
