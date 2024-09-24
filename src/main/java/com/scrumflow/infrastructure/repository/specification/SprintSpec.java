package com.scrumflow.infrastructure.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import com.scrumflow.application.dto.filter.SprintFilterDTO;
import com.scrumflow.domain.model.Sprint;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SprintSpec {
    public static Specification<Sprint> filterBy(SprintFilterDTO filterDTO) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (filterDTO.startDate() != null) {
                predicate =
                        criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), filterDTO.startDate()));
            }

            if (filterDTO.endDate() != null) {
                predicate =
                        criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), filterDTO.endDate()));
            }

            if (filterDTO.name() != null && !filterDTO.name().isEmpty()) {
                predicate =
                        criteriaBuilder.and(
                                predicate,
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("name")),
                                        "%" + filterDTO.name().toLowerCase() + "%"));
            }

            return predicate;
        };
    }
}
