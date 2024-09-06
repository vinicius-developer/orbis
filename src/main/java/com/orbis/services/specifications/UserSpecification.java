package com.orbis.services.specifications;

import com.orbis.entities.User;
import com.orbis.forms.FilterUserForm;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.criteria.Predicate;

public class UserSpecification {
    public static Specification<User> filterByForm(FilterUserForm filterForm) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterForm.getCpf() != null && !filterForm.getCpf().isEmpty()) {
                predicates.add(cb.equal(root.get("cpf"), filterForm.getCpf()));
            }

            if (filterForm.getEmail() != null && !filterForm.getEmail().isEmpty()) {
                predicates.add(cb.equal(root.get("email"), filterForm.getEmail()));
            }

            if (filterForm.getName() != null && !filterForm.getName().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filterForm.getName().toLowerCase() + "%"));
            }

            if (filterForm.getStatus() != null && !filterForm.getStatus().isEmpty()) {
                predicates.add(cb.equal(root.get("status"), filterForm.getStatus()));
            }

            if (filterForm.getInsertTimestamp() != null && !filterForm.getInsertTimestamp().isEmpty()) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("insertTimestamp"), filterForm.getInsertTimestamp()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
