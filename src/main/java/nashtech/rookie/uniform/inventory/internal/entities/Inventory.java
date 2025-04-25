package nashtech.rookie.uniform.inventory.internal.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "inventories")
@Table(name = "inventories", schema = "inventory")
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

    @Column(nullable = false)
    private Long productVariantsId;

    @Column(nullable = false)
    private Integer quantityInStock;
}
