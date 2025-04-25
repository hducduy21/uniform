package nashtech.rookie.uniform.product.internal.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "product_category")
@Table(name = "product_category", schema = "product")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
