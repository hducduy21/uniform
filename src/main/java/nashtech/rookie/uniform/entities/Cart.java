package nashtech.rookie.uniform.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="carts")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_variants_id", nullable = false)
    private ProductVariants product;

    private Integer quantity;
}
