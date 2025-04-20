package nashtech.rookie.uniform.repositories;


import nashtech.rookie.uniform.entities.Category;
import nashtech.rookie.uniform.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = {"parent"})
    List<Category> findAll();
}
