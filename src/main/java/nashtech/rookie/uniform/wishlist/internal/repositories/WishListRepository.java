package nashtech.rookie.uniform.wishlist.internal.repositories;

import nashtech.rookie.uniform.wishlist.internal.entities.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    boolean existsByUserIdAndProductId(UUID userId, UUID productId);

    Optional<WishList> findByUserIdAndProductId(UUID userId, UUID productId);

    Page<WishList> findAllByUserId(UUID userId, Pageable pageable);
}
