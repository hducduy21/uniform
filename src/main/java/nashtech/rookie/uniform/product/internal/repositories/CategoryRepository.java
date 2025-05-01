package nashtech.rookie.uniform.product.internal.repositories;


import nashtech.rookie.uniform.product.internal.entities.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = {"parent"})
    List<Category> findAll();
}
