package nashtech.rookie.uniform.product.internal.repositories;

import nashtech.rookie.uniform.product.internal.entities.ProductVariants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface ProductVariantsRepository extends JpaRepository<ProductVariants, Long> {
    Collection<ProductVariants> findAllByIdIn(Collection<Long> productIds);

    Collection<ProductVariants> findAllByProduct_Id(UUID productId);
}
