package nashtech.rookie.uniform.product.internal.repositories;

import nashtech.rookie.uniform.product.internal.entities.SizeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeGroupRepository extends JpaRepository<SizeGroup, Integer> {
}
