package nashtech.rookie.uniform.product.repositories.specs;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import nashtech.rookie.uniform.product.dtos.request.ProductFilter;
import nashtech.rookie.uniform.product.entities.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminProductSpecificationBuilder extends AbstractProductSpecificationBuilder{

    @Override
    protected void addStatusPredicate(Root<Product> root, List<Predicate> predicates, ProductFilter filter) {
        if (isNonEmpty(filter.getStatus())) {
            predicates.add(root.get("status").in(filter.getStatus()));
        }
    }
}
