package nashtech.rookie.uniform.wishlist.internal.entities;

import jakarta.persistence.*;
import lombok.*;
import nashtech.rookie.uniform.product.entities.Product;
import nashtech.rookie.uniform.user.internal.entities.User;

@Entity(name = "wishlists")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
