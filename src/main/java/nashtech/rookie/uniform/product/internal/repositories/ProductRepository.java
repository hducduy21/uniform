package nashtech.rookie.uniform.product.internal.repositories;

import nashtech.rookie.uniform.product.internal.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByIdIn(Collection<UUID> productIds, Pageable pageable);

    @EntityGraph(attributePaths = {"productVariants"})
    Optional<Product> findById(UUID productId);

    Collection<Product> findAllByIdIn(Collection<UUID> productIds);
}
