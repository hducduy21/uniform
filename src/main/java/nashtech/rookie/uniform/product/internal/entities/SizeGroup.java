package nashtech.rookie.uniform.product.internal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "sizes")
@Table(name = "sizes", schema = "product")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SizeGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    private List<String> elements;
}
