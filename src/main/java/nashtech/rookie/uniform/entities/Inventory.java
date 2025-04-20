package nashtech.rookie.uniform.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "inventory")
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

    @Column(nullable = false)
    private Double costPrice;
}
