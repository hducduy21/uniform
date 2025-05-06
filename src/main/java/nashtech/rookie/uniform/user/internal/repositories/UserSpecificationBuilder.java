package nashtech.rookie.uniform.user.internal.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import nashtech.rookie.uniform.user.internal.dtos.request.UserFilter;
import nashtech.rookie.uniform.user.internal.entities.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Component
public class UserSpecificationBuilder {

    public Specification<User> build(UserFilter filter) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            addEmailPredicate(root, builder, predicates, filter);
            addPhoneNumberPredicate(root, builder, predicates, filter);
            addLockedPredicate(root, builder, predicates, filter);
            addEnabledPredicate(root, builder, predicates, filter);
            addRolePredicate(root, builder, predicates, filter);

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    protected void addEmailPredicate(Root<User> root, CriteriaBuilder builder, List<Predicate> predicates, UserFilter filter) {
        if (isNonEmpty(filter.getEmail())) {
            predicates.add(builder.like(
                    root.get("email"),
                    "%" + filter.getEmail() + "%"
            ));
        }
    }

    protected void addPhoneNumberPredicate(Root<User> root, CriteriaBuilder builder, List<Predicate> predicates, UserFilter filter) {
        if (isNonEmpty(filter.getPhoneNumber())) {
            predicates.add(builder.equal(root.get("phoneNumber"), filter.getPhoneNumber()));
        }
    }

    protected void addLockedPredicate(Root<User> root, CriteriaBuilder builder, List<Predicate> predicates, UserFilter filter) {
        if (filter.getLocked() != null) {
            predicates.add(builder.equal(root.get("locked"), filter.getLocked()));
        }
    }

    protected void addEnabledPredicate(Root<User> root, CriteriaBuilder builder, List<Predicate> predicates, UserFilter filter) {
        if (filter.getEnabled() != null) {
            predicates.add(builder.equal(root.get("enabled"), filter.getEnabled()));
        }
    }

    protected void addRolePredicate(Root<User> root, CriteriaBuilder builder, List<Predicate> predicates, UserFilter filter) {
        if (isNonEmpty(filter.getRole().toString())) {
            predicates.add(builder.equal(root.get("role"), filter.getRole()));
        }
    }

    protected static boolean isNonEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
