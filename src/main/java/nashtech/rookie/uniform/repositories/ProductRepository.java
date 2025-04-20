package nashtech.rookie.uniform.repositories;

import nashtech.rookie.uniform.entities.Product;
import nashtech.rookie.uniform.entities.enums.EProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByStatus(EProductStatus status);

    @Query(nativeQuery = true, value = "SELECT * FROM product p JOIN (SELECT distinct product_id FROM product_category WHERE category_id = ?1) pc ON p.id = pc.product_id WHERE p.status = ?2")
    List<Product> findAllByStatusAndCategory(EProductStatus status,Long categoryId);
}
