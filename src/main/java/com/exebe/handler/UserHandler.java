package com.exebe.handler;


import com.exebe.constant.UserRole;
import com.exebe.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class UserHandler {

    public static Specification<User> createSearchUserSpec(String keyword, UserRole role) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + keyword.toLowerCase() + "%"));
            }

            if (role != null) {
                predicates.add(cb.equal(root.get("role"), role));
            }

            query.orderBy(cb.desc(root.get("id"))); // giống query.with(Sort...)

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
