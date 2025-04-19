package nashtech.rookie.uniform.entities;

import jakarta.persistence.*;
import lombok.*;
import nashtech.rookie.uniform.entities.enums.ESizeType;

@Entity(name = "sizes")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sizeName;

    @Enumerated(EnumType.STRING)
    private ESizeType sizeType;
}
