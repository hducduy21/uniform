package nashtech.rookie.uniform.wishlist.internal.repositories;

import nashtech.rookie.uniform.wishlist.internal.entities.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    boolean existsByUserIdAndProductId(UUID userId, UUID productId);

    Optional<WishList> findByUserIdAndProductId(UUID userId, UUID productId);

    Collection<WishList> findAllByUserId(UUID userId);
}
