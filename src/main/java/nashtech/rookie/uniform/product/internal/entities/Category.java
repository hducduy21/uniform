package nashtech.rookie.uniform.product.internal.entities;

import jakarta.persistence.*;
import lombok.*;
import nashtech.rookie.uniform.product.internal.entities.enums.ECategotyStatus;

import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "categories")
@Table(name = "categories", schema = "product")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Builder.Default
    private boolean isRoot = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ECategotyStatus status = ECategotyStatus.UPCOMING;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private Category parent;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private String createdBy;

    private LocalDateTime updatedAt;
    private String updatedBy;
}

