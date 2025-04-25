package nashtech.rookie.uniform.product.internal.repositories;

import nashtech.rookie.uniform.product.internal.entities.ProductVariants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantsRepository extends JpaRepository<ProductVariants, Long> {
}
