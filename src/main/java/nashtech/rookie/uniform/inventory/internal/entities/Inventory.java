package nashtech.rookie.uniform.inventory.internal.entities;

import jakarta.persistence.*;
import lombok.*;
import nashtech.rookie.uniform.product.entities.ProductVariants;

@Entity(name = "inventories")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_variants_id", nullable = false)
    private ProductVariants productVariants;

    @Column(nullable = false)
    private Integer quantityInStock;
}
