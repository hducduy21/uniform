package nashtech.rookie.uniform.product.repositories;

import nashtech.rookie.uniform.product.entities.ProductVariants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantsRepository extends JpaRepository<ProductVariants, Long> {
}
