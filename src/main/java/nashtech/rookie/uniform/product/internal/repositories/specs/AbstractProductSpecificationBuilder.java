package nashtech.rookie.uniform.product.internal.repositories.specs;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import nashtech.rookie.uniform.product.internal.dtos.request.ProductFilter;
import nashtech.rookie.uniform.product.internal.entities.Category;
import nashtech.rookie.uniform.product.internal.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProductSpecificationBuilder {
    public AbstractProductSpecificationBuilder() {
    }

    public Specification<Product> build(ProductFilter filter) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            addSearchPredicate(root, builder, predicates, filter);
            addCategoryPredicate(root, predicates, filter);
            addPricePredicates(root, builder, predicates, filter);
            addStatusPredicate(root, predicates, filter);

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    protected abstract void addStatusPredicate(Root<Product> root, List<Predicate> predicates, ProductFilter filter);

    protected void addSearchPredicate(Root<Product> root, CriteriaBuilder builder, List<Predicate> predicates, ProductFilter filter) {
        if (isNonEmpty(filter.getSearch())) {
            predicates.add(builder.like(
                    builder.lower(root.get("name")),
                    "%" + filter.getSearch().toLowerCase() + "%"
            ));
        }
    }

    protected void addCategoryPredicate(Root<Product> root, List<Predicate> predicates, ProductFilter filter) {
        if (isNonEmpty(filter.getCategories())) {
            Join<Product, Category> categoryJoin = root.join("category");
            predicates.add(categoryJoin.get("id").in(filter.getCategories()));
        }
    }

    protected void addPricePredicates(Root<Product> root, CriteriaBuilder builder, List<Predicate> predicates, ProductFilter filter) {
        if (filter.getMinPrice() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
        }
        if (filter.getMaxPrice() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
        }
    }

    protected static boolean isNonEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    protected static <T> boolean isNonEmpty(List<T> list) {
        return list != null && !list.isEmpty();
    }
}