package nashtech.rookie.uniform.entities;

import jakarta.persistence.*;
import lombok.*;

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
