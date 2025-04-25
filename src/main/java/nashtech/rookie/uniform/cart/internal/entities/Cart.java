package nashtech.rookie.uniform.cart.internal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity(name="carts")
@Table(name = "carts", schema = "cart")
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private Long productVariantsId;

    @Builder.Default
    private Integer quantity = 1;
}
