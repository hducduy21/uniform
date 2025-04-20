package nashtech.rookie.uniform.repositories;

import nashtech.rookie.uniform.entities.ProductVariants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantsRepository extends JpaRepository<ProductVariants, Long> {
}
