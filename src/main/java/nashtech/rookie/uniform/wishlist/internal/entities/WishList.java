package nashtech.rookie.uniform.wishlist.internal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name = "wishlists")
@Table(name = "wishlists", schema = "wishlist")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID productId;
}
