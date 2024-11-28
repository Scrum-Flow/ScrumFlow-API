package com.scrumflow.infrastructure.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.scrumflow.application.dto.filter.UserFilterDTO;
import com.scrumflow.domain.model.User;
import jakarta.persistence.criteria.Predicate;

public class UserSpecification {
    public static Specification<User> filterBy(UserFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (filter.projectId() != null && filter.projectId() > 0) {
                var teamsJoin = root.join("teams");
                var projectJoin = teamsJoin.join("project");

                predicate = criteriaBuilder.equal(projectJoin.get("id"), filter.projectId());
            }

            return predicate;
        };
    }
}
