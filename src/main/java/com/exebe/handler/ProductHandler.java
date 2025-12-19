package com.exebe.handler;

import com.exebe.entity.Product;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ProductHandler {

    public static Specification<Product> searchProduct(
            String keyword,
            Long categoryId,
            Double minPrice,
            Double maxPrice
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            Join<Object, Object> categoryJoin = root.join("category", JoinType.LEFT);

            if (keyword != null && !keyword.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + keyword.toLowerCase() + "%"
                        )
                );
            }

            if (categoryId != null) {
                predicates.add(
                        cb.equal(categoryJoin.get("id"), categoryId)
                );
            }

            Expression<Double> priceExpression = cb.<Double>selectCase()
                    .when(cb.isNotNull(root.get("discountedPrice")), root.get("discountedPrice"))
                    .otherwise(root.get("originalPrice"));

            if (minPrice != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(priceExpression, minPrice)
                );
            }

            if (maxPrice != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(priceExpression, maxPrice)
                );
            }

            predicates.add(cb.isTrue(root.get("isActive")));

            query.orderBy(cb.desc(root.get("id")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
