package nashtech.rookie.uniform.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "sizes")
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
    private String sizeTitle;

    @ElementCollection
    private List<String> elements;
}
