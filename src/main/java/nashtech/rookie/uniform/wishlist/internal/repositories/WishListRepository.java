package nashtech.rookie.uniform.wishlist.internal.repositories;

import nashtech.rookie.uniform.product.entities.Product;
import nashtech.rookie.uniform.user.internal.entities.User;
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

    Optional<WishList> findByUserAndProduct(User user, Product product);

    Page<WishList> findAllByUser(User user, Pageable pageable);
}
