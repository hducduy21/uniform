package nashtech.rookie.uniform.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "product_category")
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
