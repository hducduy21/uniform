package nashtech.rookie.uniform.product.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "product_variants")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "size_id", "color"}))
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ProductVariants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String color;

    private String imageUrl;

    @Builder.Default
    private boolean enabled = true;

    @Column(nullable = false)
    private Double costPrice;
}
