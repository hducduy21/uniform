package nashtech.rookie.uniform.repositories;

import nashtech.rookie.uniform.entities.SizeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeGroupRepository extends JpaRepository<SizeGroup, Integer> {
}
