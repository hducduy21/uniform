package nashtech.rookie.uniform.product.repositories.specs;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import nashtech.rookie.uniform.product.dtos.request.ProductFilter;
import nashtech.rookie.uniform.product.entities.Product;
import nashtech.rookie.uniform.shared.enums.EProductStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserProductSpecificationBuilder extends AbstractProductSpecificationBuilder {
    private static final List<String> ALLOW_STATUS_FILTER = List.of(
            EProductStatus.ACTIVE.toString(),
            EProductStatus.FEATURED.toString()
    );

    @Override
    protected void addStatusPredicate(Root<Product> root, List<Predicate> predicates, ProductFilter filter) {
        if (isNonEmpty(filter.getStatus())) {
            List<String> allowed = ALLOW_STATUS_FILTER.stream().filter(
                    filter.getStatus()::contains
            ).toList();
            predicates.add(root.get("status").in(allowed));
        }else{
            predicates.add(root.get("status").in(ALLOW_STATUS_FILTER));
        }
    }
}
